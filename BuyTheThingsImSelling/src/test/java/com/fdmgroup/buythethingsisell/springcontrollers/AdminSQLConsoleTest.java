package com.fdmgroup.buythethingsisell.springcontrollers;

import static org.junit.Assert.*;

import org.junit.Test;

public class AdminSQLConsoleTest {
	
	@Test
	public void test_gotoSQLConsole_ReturnsSQlConsole_WhenCalled(){
		AdminSQLConsole adminSqlConsole = new AdminSQLConsole();
		assertEquals("sqlConsole",adminSqlConsole.goToSQLConsole());
	}

}
