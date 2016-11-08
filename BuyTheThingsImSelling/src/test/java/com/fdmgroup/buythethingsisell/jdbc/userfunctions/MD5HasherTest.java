package com.fdmgroup.buythethingsisell.jdbc.userfunctions;

import static org.junit.Assert.*;

import org.junit.Test;

public class MD5HasherTest {
	
	@Test
	public void test_GetPasswordReturnsString_WhenCalledWithPassword(){
		String password = "password";
		String expected = "5f4dcc3b5aa765d61d8327deb882cf99";
		String actual = MD5Hasher.getMD5Hashed(password);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_GetPasswordReturnsString_WhenCalledWithHello(){
		String password = "Hello";
		String expected = "8b1a9953c4611296a827abf8c47804d7";
		String actual = MD5Hasher.getMD5Hashed(password);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_GetPasswordReturnsString_WhenCalledWithZcxcuyzxcsaiuydsfsdFSDFDSS(){
		String password = "ZcxcuyzxcsaiuydsfsdFSDFDSS";
		String expected = "64c0ec1638ce904f38f38636700ee512";
		String actual = MD5Hasher.getMD5Hashed(password);
		assertEquals(expected, actual);
	}

}
