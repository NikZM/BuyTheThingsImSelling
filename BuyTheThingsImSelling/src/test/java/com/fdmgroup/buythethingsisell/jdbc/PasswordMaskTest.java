package com.fdmgroup.buythethingsisell.jdbc;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordMaskTest {
	
	@Test
	public void test_getPassword_ReturnsString(){
		assertTrue(PasswordMask.getPassword().getClass() == String.class);
	}

}
