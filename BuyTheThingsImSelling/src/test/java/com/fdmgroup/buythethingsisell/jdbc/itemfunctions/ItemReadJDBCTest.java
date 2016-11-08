package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;

public class ItemReadJDBCTest {

	@InjectMocks private ItemReadJDBC itemReadJDBC;
	@Mock private ItemEntityBuilder mockItemEntityBuilder;
	@Mock private ConnectionFactory mockConnectionFactory;
	@Mock private Connection mockConnection;
	@Mock private PreparedStatement mockPreparedStmt;
	@Mock private ResultSet mockResultSet;
	@Mock private ItemsEntityWrapper mockItemEntityWrapper;
	@Mock private ItemEntity mockItemEntity;
	
	@Before
	public void setup() throws SQLException{
		MockitoAnnotations.initMocks(this);
		when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmt);
		when(mockPreparedStmt.executeQuery()).thenReturn(mockResultSet);
		when(mockItemEntityBuilder.getItemsByPage(mockResultSet)).thenReturn(mockItemEntityWrapper);
	}
	
	@Test
	public void test_GetItemByID_ConnectionFactoryCallsGetConnection_WhenCalled(){
		itemReadJDBC.getItemByID(1);
		verify(mockConnectionFactory).getConnection();
	}
	
	@Test
	public void test_GetItemByID_ThreePreparedStatementsAreMade_WhenCalled() throws SQLException{
		itemReadJDBC.getItemByID(1);
		verify(mockConnection,times(3)).prepareStatement(anyString());
	}
	
	@Test
	public void test_GetItemByID_SetIntIsCalledByPreparedStatementsThreeTimes_WhenCalled() throws SQLException{
		itemReadJDBC.getItemByID(1);
		verify(mockPreparedStmt,times(3)).setInt(1, 1);
	}
	
	@Test
	public void test_GetItemByID_ExecuteQueryIsCalledThreeTimesOneForEachPreparedStatement_WhenCalled() throws SQLException{
		itemReadJDBC.getItemByID(1);
		verify(mockPreparedStmt,times(3)).executeQuery();
	}
	
	@Test
	public void test_GetItemByID_ItemEntityBuilderIsCalledAndReturnsItemEntity_WhenCalled() throws SQLException{
		when(mockItemEntityBuilder.getItemByID(anyObject(), anyObject(), anyObject())).thenReturn(mockItemEntity);
		ItemEntity ie = itemReadJDBC.getItemByID(1);
		verify(mockItemEntityBuilder).getItemByID(mockResultSet, mockResultSet, mockResultSet);
		assertEquals(mockItemEntity, ie);
	}
	
	@Test
	public void test_GetItemByID_VerifyCatchClauseIsPassed_WhenConnectionPreparedStatementThrowsAnError() throws SQLException{
		SQLException mockException = mock(SQLException.class);
		when(mockConnection.prepareStatement(anyString())).thenThrow(mockException);
		itemReadJDBC.getItemByID(1);
	}
	
	@Test
	public void test_GetItemByID_VerifyConnectionIsClosed_WhenCalled() throws SQLException{
		itemReadJDBC.getItemByID(1);
		verify(mockConnection).close();
	}
	
	@Test
	public void test_GetItemByID_VerifyCatchClauseIsPassed_WhenConnectionClosedThrowsAnException() throws SQLException{
		SQLException mockException = mock(SQLException.class);
		doThrow(mockException).when(mockConnection).close();
		itemReadJDBC.getItemByID(1);
	}
	
	
	@Test
	public void test_GetItemsByPage_ConnectionFactoryCallsGetConnection_WhenCalled(){
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
		verify(mockConnectionFactory).getConnection();
	}
	
	@Test
	public void test_GetItemsByPage_TwoPreparedStatementsAreMade_WhenCalled() throws SQLException{
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
		verify(mockConnection,times(2)).prepareStatement(anyString());
	}
	
	@Test
	public void test_GetItemsByPage_SetIntIsCalledByPreparedStatements_WhenCalled() throws SQLException{
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
		verify(mockPreparedStmt).setInt(1, lowBound);
		verify(mockPreparedStmt).setInt(2, upperBound);
	}
	
	@Test
	public void test_GetItemsByPage_ExecuteQueryIsCalledTwice_WhenCalled() throws SQLException{
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
		verify(mockPreparedStmt,times(2)).executeQuery();
	}
	
	@Test
	public void test_GetItemsByPage_ItemEntityBuilderIsCalledAndReturnsItemEntityWrapper_WhenCalled() throws SQLException{
		when(mockItemEntityBuilder.getItemsByPage(anyObject())).thenReturn(mockItemEntityWrapper);
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		ItemsEntityWrapper iew = itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
		verify(mockItemEntityBuilder).getItemsByPage(mockResultSet);
		assertEquals(mockItemEntityWrapper, iew);
	}
	
	@Test
	public void test_GetItemsByPage_VerifyCatchClauseIsPassed_WhenConnectionPreparedStatementThrowsAnError() throws SQLException{
		SQLException mockException = mock(SQLException.class);
		when(mockConnection.prepareStatement(anyString())).thenThrow(mockException);
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
	}
	
	@Test
	public void test_GetItemsByPage_VerifyConnectionIsClosed_WhenCalled() throws SQLException{
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
		verify(mockConnection).close();
	}
	
	@Test
	public void test_GetItemsByPage_VerifyCatchClauseIsPassed_WhenConnectionClosedThrowsAnException() throws SQLException{
		SQLException mockException = mock(SQLException.class);
		doThrow(mockException).when(mockConnection).close();
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
	}
	
	@Test
	public void test_GetItemsByPage_ResultSetNextIsCalled_WhenCalled() throws SQLException{
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
		verify(mockResultSet).next();
	}
	
	@Test
	public void test_GetItemsByPage_ResultSetGetDoubleIsCalled_WhenCalled() throws SQLException{
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
		verify(mockResultSet).getDouble(1);
	}
	
	@Test
	public void test_GetItemsByPage_SetPageNumberIsCalled_WhenCalled() throws SQLException{
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
		verify(mockItemEntityWrapper).setPageNumber(pageNumInh);
	}
	
	@Test
	public void test_GetItemsByPage_SetPageMaxIsCalledAndReturnsCorrectNumberOfPagesAsTwo_WhenCalledWithLBOneUBSevenAndRowCountTwelve() throws SQLException{
		when(mockResultSet.getDouble(1)).thenReturn(12.0);
		int lowBound = 1, upperBound = 7, pageNumInh = 1;
		itemReadJDBC.getItemsByPage(lowBound, upperBound, pageNumInh);
		
		verify(mockItemEntityWrapper).setPageMax(2);
	}
	
}
