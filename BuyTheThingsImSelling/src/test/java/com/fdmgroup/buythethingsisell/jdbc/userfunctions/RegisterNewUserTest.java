package com.fdmgroup.buythethingsisell.jdbc.userfunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.fdmgroup.buythethingsisell.entities.User;
import com.fdmgroup.buythethingsisell.jdbc.ConnectionFactory;
import com.fdmgroup.buythethingsisell.jdbc.SQLQueries;

public class RegisterNewUserTest {
	
	@InjectMocks private RegisterNewUser registerNewUser;
	@Mock private ConnectionFactory mockConnectionFactory;
	@Mock private Connection mockConnection;
	@Mock private Statement mockNextIDStatement;
	@Mock private ResultSet mockNextIDResSet;
	@Mock private PreparedStatement mockAddUserStmt;
	@Mock private PreparedStatement mockAddUserRolesStmt;
	@Mock private PreparedStatement mockAddExInfoStmt;
	@Mock private User mockUser;
	
	@Before
	public void setUp() throws SQLException{
		MockitoAnnotations.initMocks(this);
		when(mockConnectionFactory.getConnection()).thenReturn(mockConnection);
		when(mockConnection.createStatement()).thenReturn(mockNextIDStatement);
		when(mockNextIDStatement.executeQuery(anyString())).thenReturn(mockNextIDResSet);
		when(mockNextIDResSet.next()).thenReturn(true);
		when(mockNextIDResSet.getInt(1)).thenReturn(10);
		when(mockConnection.prepareStatement(SQLQueries.AddUser.query())).thenReturn(mockAddUserStmt);
		when(mockAddUserStmt.executeUpdate()).thenReturn(1);
		when(mockConnection.prepareStatement(SQLQueries.AddRoles.query())).thenReturn(mockAddUserRolesStmt);
		when(mockAddUserRolesStmt.executeUpdate()).thenReturn(1);
		when(mockConnection.prepareStatement(SQLQueries.AddUserExInfo.query())).thenReturn(mockAddExInfoStmt);
		when(mockAddExInfoStmt.executeUpdate()).thenReturn(1);
		
	}
	
	@Test
	public void test_registerNewUser_CallsGetConnectionWhenCalled(){
		registerNewUser.registerNewUser(mockUser);
		verify(mockConnectionFactory).getConnection();
	}
	
	@Test
	public void test_registerNewUser_ClosesConnection_WhenCalled() throws SQLException{
		registerNewUser.registerNewUser(mockUser);
		verify(mockConnection).close();
	}
	
	@Test
	public void test_registerNewUser_ConnectionCallsCommit_WithDefaultParams() throws SQLException{
		registerNewUser.registerNewUser(mockUser);
		verify(mockConnection).commit();
	}
	
	@Test
	public void test_registerNewUser_ConnectionCallsRollBackWhenARowReturnsNotOne() throws SQLException{
		when(mockAddExInfoStmt.executeUpdate()).thenReturn(0);
		registerNewUser.registerNewUser(mockUser);
		verify(mockConnection,never()).commit();
		verify(mockConnection).rollback();
	}
	
	@Test
	public void test_registerNewUser_ReturnsFalse_WhenExceptionIsThrown() throws SQLException{
		SQLException mockException = mock(SQLException.class);
		doThrow(mockException).when(mockConnection).commit();
		Boolean b = registerNewUser.registerNewUser(mockUser);
		assertEquals(false, b);
	}
	
	@Test
	public void test_registerNewUser_GoesToCatchClause_WhenConnectionCloseThrowsException() throws SQLException{
		SQLException mockException = mock(SQLException.class);
		doThrow(mockException).when(mockConnection).close();
		registerNewUser.registerNewUser(mockUser);
	}

}
