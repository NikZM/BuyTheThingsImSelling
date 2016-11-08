package com.fdmgroup.buythethingsisell.jdbc.userfunctions;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdmgroup.buythethingsisell.entities.JSONConverter;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;

public class AdminSQLDirectInterfaceTest {
	
	@InjectMocks private AdminSQLDirectInterface adminDI;
	@Mock private ConnectionFactory mockConnectionFactory;
	@Mock private Connection mockConnection;
	@Mock private Statement mockStatement;
	@Mock private ResultSet mockResultSet;
	@Mock private ResultSetMetaData mockResultSetMetaData;
	@Mock private Blob mockBlob;
	@Mock private SQLException mockException;
	@Mock private JSONConverter mockJSONConverter;
	
	@Before
	public void setup() throws SQLException{
		MockitoAnnotations.initMocks(this);
		when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
		when(mockResultSet.getMetaData()).thenReturn(mockResultSetMetaData);
		when(mockResultSetMetaData.getColumnCount()).thenReturn(3);
		when(mockResultSetMetaData.getColumnName(anyInt())).thenReturn("Row Number","header2","header3");
		when(mockResultSet.next()).thenReturn(true,true,true, false);
		when(mockResultSet.getString(anyInt())).thenReturn("Row1","1","2","Row2","3","4","Row3","5","6");
		when(mockResultSet.getBlob(anyInt())).thenReturn(mockBlob);
		when(mockStatement.executeUpdate(anyString())).thenReturn(5);
	}
	
	@Test
	public void test_executeQuery_CallsGetConnection_WhenCalled() throws SQLException{
		adminDI.executeQuery("SELECT * FROM TABLE");
		verify(mockConnectionFactory).getConnection();
	}
	
	@Test
	public void test_executeQuery_CallsConnectionClose_WhenCalled() throws SQLException{
		adminDI.executeQuery("SELECT * FROM TABLE");
		verify(mockConnection).close();
	}
		
	@Test
	public void test_executeQuery_TriesBlobIfStringCastFails() throws SQLException{
		when(mockResultSet.getString(anyInt())).thenReturn("Row1","1","2","Row2","3","4","Row3","5").thenThrow(mockException);
		adminDI.executeQuery("SELECT * FROM TABLE");
		verify(mockResultSet).getBlob(anyInt());
	}
	
	@Test
	public void test_executeQuery_InsertsNullIfNotBlob() throws SQLException{
		when(mockResultSet.getString(anyInt())).thenReturn("Row1","1","2","Row2","3","4","Row3","5").thenThrow(mockException);
		when(mockResultSet.getBlob(anyInt())).thenThrow(mockException);
		adminDI.executeQuery("SELECT * FROM TABLE");
	}
	
	@Test
	public void test_executeUpdate_verifyConnectionCloses_WhenCalled() throws SQLException{
		adminDI.executeUpdate("DELETE * FROM TABLE");
		verify(mockConnection).close();
	}

}
