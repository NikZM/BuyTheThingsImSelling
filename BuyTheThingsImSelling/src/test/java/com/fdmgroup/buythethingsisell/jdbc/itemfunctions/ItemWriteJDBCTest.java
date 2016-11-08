package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class ItemWriteJDBCTest {
	@InjectMocks private ItemWriteJDBC itemWriteJDBC;
	@Mock private ConnectionFactory mockConnectionFactory;
	@Mock private Connection mockConnection;
	@Mock private PreparedStatement mockItemsPreparedStmt;
	@Mock private PreparedStatement mockStockPreparedStmt;
	@Mock private PreparedStatement mockCategoryPreparedStmt;
	@Mock private PreparedStatement mockUserPreparedStmt;
	@Mock private PreparedStatement mockGetUserIDPreparedStmt;
	@Mock private ResultSet mockUserIDResultSet;
	@Mock private ResultSet mockResultSet;
	@Mock private Statement mockNextIDStmt;
	@Mock private ResultSet mockNextIDResSet;
	@Mock private ItemEntity mockItemEntity;
	@Mock private Blob mockBlob;
	
	@SuppressWarnings("serial")
	@Before
	public void setup() throws SQLException{
		MockitoAnnotations.initMocks(this);
		when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
		when(mockConnection.createStatement()).thenReturn(mockNextIDStmt);
		when(mockNextIDStmt.executeQuery(anyString())).thenReturn(mockNextIDResSet);
		when(mockNextIDResSet.next()).thenReturn(true);
		when(mockNextIDResSet.getInt(1)).thenReturn(10);
		when(mockConnection.prepareStatement("SELECT USERID FROM USERS WHERE EMAIL = ?")).thenReturn(mockGetUserIDPreparedStmt);
		when(mockGetUserIDPreparedStmt.executeQuery()).thenReturn(mockUserIDResultSet);
		when(mockUserIDResultSet.next()).thenReturn(true);
		when(mockUserIDResultSet.getInt(1)).thenReturn(1);
		when(mockConnection.prepareStatement(SQLQueries.AddItem.query())).thenReturn(mockItemsPreparedStmt);
		when(mockItemsPreparedStmt.executeUpdate()).thenReturn(1);
		when(mockConnection.prepareStatement(SQLQueries.UpdateItemStock.query())).thenReturn(mockStockPreparedStmt);
		when(mockStockPreparedStmt.executeUpdate()).thenReturn(1);
		when(mockConnection.prepareStatement(SQLQueries.AddItemToCategory.query())).thenReturn(mockCategoryPreparedStmt);
		when(mockConnection.prepareStatement(SQLQueries.AddItemToUser.query())).thenReturn(mockUserPreparedStmt);
		when(mockUserPreparedStmt.executeUpdate()).thenReturn(1);
		when(mockConnection.createBlob()).thenReturn(mockBlob);
		when(mockConnection.isClosed()).thenReturn(true);
		
		when(mockItemEntity.getDescription()).thenReturn("hello");
		when(mockItemEntity.getCategories()).thenReturn(new ArrayList<String>(){{add("cat1");add("cat2");}});
		
	}
	
	@Test
	public void test_postItem_GetConnectionIsCalledOnce_WhenCalledWithItemEntityAndUserEmail(){
		itemWriteJDBC.postItem(mockItemEntity, "email");
		verify(mockConnectionFactory).getConnection();
	}
	
	@Test
	public void test_postItem_CloseConnectionIsCalledOnce_WhenCalledWithItemEntityAndUserEmail() throws SQLException{
		itemWriteJDBC.postItem(mockItemEntity, "email");
		verify(mockConnection).close();
	}
	
	@Test
	public void test_postItem_VerifyStatementsAreCommited_WhenCalledWithItemEntityAndUserEmail() throws SQLException{
		itemWriteJDBC.postItem(mockItemEntity, "email");
		verify(mockConnection).commit();
	}
	
	@Test
	public void test_postItem_ConnectionIsCreatedWhenGivenNoConnection_WhenCalledWithItemEntityAndUserEmailAndNoConnection() throws SQLException{
		itemWriteJDBC.postItem(mockItemEntity, 1);
		verify(mockConnectionFactory).getConnection();
	}

	@Test
	public void test_getNextItemSeqID_ThrowsError_WhenResultSetHasNoNext() throws SQLException{
		when(mockNextIDResSet.next()).thenReturn(false);
		boolean b = itemWriteJDBC.postItem(mockItemEntity, "email");
		assertEquals(false, b);
	}
	
	@Test
	public void test_postItem_ReturnsFalse_WhenNoMatchForEmail() throws SQLException{
		when(mockUserIDResultSet.next()).thenReturn(false);
		boolean b = itemWriteJDBC.postItem(mockItemEntity, "email");
		assertEquals(false, b);
	}
	
	@Test
	public void test_postItem_ConnectionCloses_IfNotAlreadyClosed() throws SQLException{
		when(mockConnection.isClosed()).thenReturn(false);
		itemWriteJDBC.postItem(mockItemEntity, "email");
		verify(mockConnection,times(2)).close();
	}
	
	@Test
	public void test_postItem_verifyErrorIsThrown_WhenConnectionCloseThrowsError() throws SQLException{
		when(mockConnection.isClosed()).thenReturn(false);
		SQLException mockException = mock(SQLException.class);
		doThrow(mockException).when(mockConnection).close();
		itemWriteJDBC.postItem(mockItemEntity, "email");
	}
	
	@Test
	public void test_postItem_verifyErrorIsThrown_WhenConnectionIsClosedThrowsError() throws SQLException{
		SQLException mockException = mock(SQLException.class);
		doThrow(mockException).when(mockConnection).isClosed();
		itemWriteJDBC.postItem(mockItemEntity, "email");
	}
	
	@Test
	public void test_postItem_verifyConnectionCloseGetsCalledOnce_WhenConnectionIsClosedReturnTrue() throws SQLException{
		when(mockConnection.isClosed()).thenReturn(true);
		itemWriteJDBC.postItem(mockItemEntity, "email");
		verify(mockConnection).close();
	}
	
	@Test
	public void test_postItem_RollBackIsCalled_WhenResultSetForUserPrepStmtReturnNotOne() throws SQLException{
		when(mockUserPreparedStmt.executeUpdate()).thenReturn(0);
		itemWriteJDBC.postItem(mockItemEntity, "email");
		verify(mockConnection).rollback();
	}
	
	@Test
	public void test_postItem_RollBackIsCalled_WhenResultSetForStockPrepStmtReturnNotOne() throws SQLException{
		when(mockStockPreparedStmt.executeUpdate()).thenReturn(0);
		itemWriteJDBC.postItem(mockItemEntity, "email");
		verify(mockConnection).rollback();
	}
	
	@Test
	public void test_postItem_RollBackIsCalled_WhenResultSetForItemPrepStmtReturnNotOne() throws SQLException{
		when(mockItemsPreparedStmt.executeUpdate()).thenReturn(0);
		itemWriteJDBC.postItem(mockItemEntity, "email");
		verify(mockConnection).rollback();
	}
}
