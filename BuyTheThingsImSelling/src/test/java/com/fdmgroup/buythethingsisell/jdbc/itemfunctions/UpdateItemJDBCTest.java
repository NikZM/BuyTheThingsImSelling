package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;

public class UpdateItemJDBCTest {
	
	@InjectMocks private UpdateItemJDBC updateItemJDBC;
	@Mock private ConnectionFactory mockConnectionFactory;
	@Mock private Connection mockConnection;
	@Mock private PreparedStatement mockPreparedStmt;
	@Mock private ResultSet mockResultSet;
	@Mock private ItemEntity mockItemEntity;
	@Mock private Blob mockBlob;

	@SuppressWarnings("serial")
	@Before
	public void setup() throws SQLException{
		MockitoAnnotations.initMocks(this);
		when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmt);
		when(mockPreparedStmt.executeQuery()).thenReturn(mockResultSet);
		when(mockPreparedStmt.executeUpdate()).thenReturn(1);
		when(mockConnection.createBlob()).thenReturn(mockBlob);
		when(mockItemEntity.getDescription()).thenReturn("hello");
		when(mockItemEntity.getCategories()).thenReturn(new ArrayList<String>(){{add("cat1");add("cat2");}});
	}
	
	@Test
	public void test_updateItem_callsGetConnection_WhenCalled(){
		updateItemJDBC.updateItem(mockItemEntity);
		verify(mockConnectionFactory).getConnection();
	}
	
	@Test
	public void test_updateItem_createsFourPrepareStatements_WhenCalled() throws SQLException{
		updateItemJDBC.updateItem(mockItemEntity);
		verify(mockConnection,times(4)).prepareStatement(anyString());
	}
	
	@Test
	public void test_updateItem_ExecuteUpdateIsCalledThreeTimesAndExecuteBatchOnce_WhenCalled() throws SQLException{
		updateItemJDBC.updateItem(mockItemEntity);
		verify(mockPreparedStmt,times(3)).executeUpdate();
		verify(mockPreparedStmt).executeBatch();
	}
	
	@Test
	public void test_updateItem_AddBatchIsCalledTwice_WhenGetCategoriesReturnsTwoItems() throws SQLException{
		updateItemJDBC.updateItem(mockItemEntity);
		verify(mockPreparedStmt,times(2)).addBatch();
	}
	
	@Test
	public void test_updateItem_ConnectionCommitIsCalledAndMethodReturnTrue_WhenCalledWithSetUpParams() throws SQLException{
		boolean b = updateItemJDBC.updateItem(mockItemEntity);
		verify(mockConnection).commit();
		assertEquals(true, b);
	}
	
	@Test
	public void test_updateItem_ConnectionRollBackIsCalledAndMethodReturnFalse_WhenFirstStatementReturnsZero() throws SQLException{
		when(mockPreparedStmt.executeUpdate()).thenReturn(0).thenReturn(1);
		boolean b = updateItemJDBC.updateItem(mockItemEntity);
		verify(mockConnection,never()).commit();
		verify(mockConnection).rollback();
		assertEquals(false, b);
	}
	
	@Test
	public void test_updateItem_ConnectionRollBackIsCalledAndMethodReturnFalse_WhenSecondStatementReturnsZero() throws SQLException{
		when(mockPreparedStmt.executeUpdate()).thenReturn(1).thenReturn(0);
		boolean b = updateItemJDBC.updateItem(mockItemEntity);
		verify(mockConnection,never()).commit();
		verify(mockConnection).rollback();
		assertEquals(false, b);
	}
	
	@Test
	public void test_updateItem_verifyConnectionCloses_WhenCalled() throws SQLException{
		updateItemJDBC.updateItem(mockItemEntity);
		verify(mockConnection).close();
	}
	
	@Test
	public void test_updateItem_verifyCatchClause_WhenCloseThrowsException() throws SQLException{
		SQLException mockException = mock(SQLException.class);
		doThrow(mockException).when(mockConnection).close();
		updateItemJDBC.updateItem(mockItemEntity);
	}
	
	@Test
	public void test_updateItem_verifyCatchClause_WhenCommitThrowsException() throws SQLException{
		SQLException mockException = mock(SQLException.class);
		doThrow(mockException).when(mockConnection).commit();
		updateItemJDBC.updateItem(mockItemEntity);
	}
	

}
