package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

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

import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class ItemByCategoryJDBCTest {

	@InjectMocks private ItemByCategoryJDBC itemCategoryJDBC;
	@Mock private ItemEntityBuilder mockItemEntityBuilder;
	@Mock private ConnectionFactory mockConnectionFactory;
	@Mock private Connection mockConnection;
	@Mock private PreparedStatement mockPreparedStmt;
	@Mock private ResultSet mockResultSet;
	@Mock private ItemsEntityWrapper mockItemEntityWrapper;
	
	@Before
	public void setup() throws SQLException{
		MockitoAnnotations.initMocks(this);
		when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmt);
		when(mockPreparedStmt.executeQuery()).thenReturn(mockResultSet);
		when(mockItemEntityBuilder.getItemsByPage(mockResultSet)).thenReturn(mockItemEntityWrapper);
	}
	
	@Test
	public void test_OnePreparedStatementIsMade_WhenMethodCalled() throws SQLException{
		itemCategoryJDBC.getItemsByCategory("cat1");
		verify(mockConnection).prepareStatement(SQLQueries.GetItemsByCategory.query());
	}
	
	@Test
	public void test_preparedStatementHasFirstIntSetToCatName() throws SQLException{
		String catName = "cat1";
		itemCategoryJDBC.getItemsByCategory(catName);
		verify(mockPreparedStmt).setString(1, catName);
	}
	
	@Test
	public void test_verifyExecuteQueryIsCalled() throws SQLException{
		String catName = "cat1";
		itemCategoryJDBC.getItemsByCategory(catName);
		verify(mockPreparedStmt).executeQuery();
	}
	
	@Test
	public void test_verifyItemEntityBuilderIsCalledOnceWithResultSet() throws SQLException{
		String catName = "cat1";
		itemCategoryJDBC.getItemsByCategory(catName);
		verify(mockItemEntityBuilder).getItemsByPage(mockResultSet);
	}
	
	@Test
	public void test_verifyItemEntityWrapperReturnedIsSame() throws SQLException{
		String catName = "cat1";
		ItemsEntityWrapper assertedEntity = itemCategoryJDBC.getItemsByCategory(catName);
		assertEquals(mockItemEntityWrapper, assertedEntity);
	}
	
	@Test
	public void test_GoesToCatchClause_WhenExceptionIsThrownByPreparedStatement() throws SQLException{
		String catName = "cat1";
		SQLException mockException = mock(SQLException.class);
		when(mockPreparedStmt.executeQuery()).thenThrow(mockException);
		itemCategoryJDBC.getItemsByCategory(catName);
	}
	
	@Test
	public void test_VerifyConnectionIsClosed_WhenMethodIsCalled() throws SQLException{
		String catName = "cat1";
		itemCategoryJDBC.getItemsByCategory(catName);
		verify(mockConnection).close();
	}
	
	@Test
	public void test_VerifyGoesToCatchClauseForConnectionClose_WhenConnectionCloseThrowsException() throws SQLException{
		String catName = "cat1";
		SQLException mockException = mock(SQLException.class);
		doThrow(mockException).when(mockConnection).close();
		itemCategoryJDBC.getItemsByCategory(catName);
	}
	
}
