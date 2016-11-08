package com.fdmgroup.buythethingsisell;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.buythethingsisell.restcontrollers.RestController;

public class MyApplicationTest {

	private MyApplication myApp;
	
	@Before
	public void setUp(){
		myApp = new MyApplication();
	}
	
	@Test
	public void test_GetClassesContainsRestControllerClass(){
		Set<Class<?>> s = myApp.getClasses();
		assertTrue(s.contains(RestController.class));
	}
}
