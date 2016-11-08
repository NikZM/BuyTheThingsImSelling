package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class DeleteItemJDBCTest {
	
	@InjectMocks private DeleteItemJDBC deleteItemJDBC;
	@Mock private ConnectionFactory mockConnectionFactory;
	@Mock private Connection mockConnection;
	@Mock private PreparedStatement mockDeleteUsersStmt;
	@Mock private PreparedStatement mockDeleteStockStmt;
	@Mock private PreparedStatement mockDeleteCatStmt;
	@Mock private PreparedStatement mockDeleteItemStmt;
	
	@Before
	public void setUp() throws SQLException{
		MockitoAnnotations.initMocks(this);
		when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(SQLQueries.DeleteUsersItemsByID.query())).thenReturn(mockDeleteUsersStmt);
		when(mockDeleteUsersStmt.executeUpdate()).thenReturn(1);
		when(mockConnection.prepareStatement(SQLQueries.DeleteItemStockByID.query())).thenReturn(mockDeleteStockStmt);
		when(mockDeleteStockStmt.executeUpdate()).thenReturn(1);
		when(mockConnection.prepareStatement(SQLQueries.DropItemCategories.query())).thenReturn(mockDeleteCatStmt);
		when(mockDeleteCatStmt.executeUpdate()).thenReturn(3);
		when(mockConnection.prepareStatement(SQLQueries.DeleteItemByID.query())).thenReturn(mockDeleteItemStmt);
		when(mockDeleteItemStmt.executeUpdate()).thenReturn(1);
	}
	
	@Test
	public void test_verifyAutoCommitFalseIsCalled_WhenMethodIsCalled() throws SQLException{
		deleteItemJDBC.deleteItem(1);
		verify(mockConnection).setAutoCommit(false);
	}
	
	@Test
	public void test_verifyFourPreparedStatementsAreCalled() throws SQLException{
		deleteItemJDBC.deleteItem(1);
		verify(mockConnection,times(4)).prepareStatement(anyString());
	}
	
	@Test
	public void test_verifyPreparedStatementsAreSetWithMethodInt() throws SQLException{
		int itemID = 1;
		deleteItemJDBC.deleteItem(itemID);
		verify(mockDeleteUsersStmt).setInt(1, itemID);
		verify(mockDeleteStockStmt).setInt(1, itemID);
		verify(mockDeleteCatStmt).setInt(1, itemID);
		verify(mockDeleteCatStmt).setInt(1, itemID);
	}

	@Test
	public void test_verifyExecuteUpdateIsCalledForAllPreparedStatements() throws SQLException{
		deleteItemJDBC.deleteItem(1);
		verify(mockDeleteUsersStmt).executeUpdate();
		verify(mockDeleteStockStmt).executeUpdate();
		verify(mockDeleteCatStmt).executeUpdate();
		verify(mockDeleteCatStmt).executeUpdate();
	}
	
	@Test
	public void test_verifyCommitIsCalledWhenAppropiateExecutesReturnOne() throws SQLException{
		deleteItemJDBC.deleteItem(1);
		verify(mockConnection).commit();
	}
	
	@Test
	public void test_verifyRollbackIsCalledWhenUsersReturnNotOne() throws SQLException{
		when(mockDeleteUsersStmt.executeUpdate()).thenReturn(0);
		deleteItemJDBC.deleteItem(1);
		verify(mockConnection).rollback();
	}
	
	@Test
	public void test_verifyRollbackIsCalledWhenStockReturnNotOne() throws SQLException{
		when(mockDeleteStockStmt.executeUpdate()).thenReturn(0);
		deleteItemJDBC.deleteItem(1);
		verify(mockConnection).rollback();
	}
	
	@Test
	public void test_verifyRollbackIsCalledWhenItemReturnNotOne() throws SQLException{
		when(mockDeleteItemStmt.executeUpdate()).thenReturn(0);
		deleteItemJDBC.deleteItem(1);
		verify(mockConnection).rollback();
	}
	
	@Test
	public void test_verifyRollBackIsNotCalledWhenCategoriesIsNotOne() throws SQLException{
		when(mockDeleteCatStmt.executeUpdate()).thenReturn(0);
		deleteItemJDBC.deleteItem(1);
		verify(mockConnection,never()).rollback();
	}
	
	@Test
	public void test_AutoCommitIsSetToTrue() throws SQLException{
		deleteItemJDBC.deleteItem(1);
		verify(mockConnection).setAutoCommit(true);
	}
	
	@Test
	public void test_submittedIsFalseWhenExceptionIsThrown() throws SQLException{
		doThrow(mock(SQLException.class)).when(mockConnection).commit();
		Boolean b = deleteItemJDBC.deleteItem(1);
		assertEquals(false,b);
	}
}
