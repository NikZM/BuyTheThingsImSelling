package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryReaderJDBCTest {
	
	@InjectMocks private CategoryReaderJDBC categoryReaderJDBC;
	@Mock private ConnectionFactory mockConnectionFactory;
	@Mock private Connection mockConnection;
	@Mock private PreparedStatement mockPreparedStmt;
	@Mock private ResultSet mockResSet;
	
	
	@Before
	public void setUp() throws SQLException{
		MockitoAnnotations.initMocks(this);
		when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmt);
		when(mockPreparedStmt.executeQuery()).thenReturn(mockResSet);
		when(mockResSet.next()).thenReturn(true, true, true, false);
		when(mockResSet.getString(anyInt())).thenReturn("cat1", "cat2", "cat3");
	}
	
	@Test
	public void test_() throws SQLException{
		List<String> c = categoryReaderJDBC.getAllCategories();
		verify(mockResSet,times(3)).getString(anyInt());
		assertEquals("[cat1, cat2, cat3]",c.toString());
	}
	
	@Test
	public void test_eror() throws SQLException{
		when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException());
		categoryReaderJDBC.getAllCategories();
		verify(mockResSet, never()).getString(anyInt());
	}
	
	@Test
	public void test_finally() throws SQLException {
		categoryReaderJDBC.getAllCategories();
		verify(mockConnection).close();
	}
	
	@Test
	public void test_finally_error() throws SQLException {
		doThrow(mock(SQLException.class)).when(mockConnection).close();
		categoryReaderJDBC.getAllCategories();
		verify(mockConnection).close();
	}

}
