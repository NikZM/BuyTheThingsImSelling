package com.fdmgroup.buythethingsisell.entities;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class JSONConverterTest {
	
	private JSONConverter jsonConverter;
	
	@Before
	public void setup(){
		this.jsonConverter = new JSONConverter();
	}
	
	
	@Test
	public void test_returnsStringWhenCalled() throws ClassNotFoundException{
		String s = jsonConverter.toJsonField("fn", "attr");
		assertTrue(s.getClass() == Class.forName("java.lang.String"));
	}
	
	@Test
	public void  test_wrapsStringInQuotes_WhenCalledWithString(){
		String s = jsonConverter.toJsonField("fn", "attr");
		assertEquals("\"fn\":\"attr\"", s);
	}
	
	@Test
	public void  test_noQuoteWrapObInteger_WhenCalledWithInteger(){
		String s = jsonConverter.toJsonField("fn", 1);
		assertEquals("\"fn\":1", s);
	}
	
	@Test
	public void  test_returnsJSONArray_WhenCalledWithStringArray(){
		@SuppressWarnings("serial")
		List<String> list = new ArrayList<String>(){{
			add("attr");
			add("attr2");
			add("attr3");
		}};
		String s = jsonConverter.toJsonField("fn", list);
		assertEquals("\"fn\":[\"attr\",\"attr2\",\"attr3\"]", s);
	}
	
	@Test
	public void  test_returnsJSONArrayWithNoQuotes_WhenCalledWithIntegerArray(){
		@SuppressWarnings("serial")
		List<Integer> list = new ArrayList<Integer>(){{
			add(1);
			add(2);
			add(3);
		}};
		String s = jsonConverter.toJsonField("fn", list);
		assertEquals("\"fn\":[1,2,3]", s);
	}
	
	@Test
	public void  test_returnsEmptyValidJSOnArray_WhenCalledWithEmptyArray(){
		List<Object> list = new ArrayList<Object>();
		String s = jsonConverter.toJsonField("fn", list);
		assertEquals("\"fn\":[]", s);
	}
	
	@Test
	public void  test_returnsStringWithNoWrappedQuote_WhenCalledWithStringAndOverloadedBooleanTrue(){
		String s = jsonConverter.toJsonField("fn", "attr",true);
		assertEquals("\"fn\":attr", s);
	}
	
	@Test
	public void  test_returnsStringWithWrappedQuote_WhenCalledWithStringAndOverloadedBooleanFalse(){
		String s = jsonConverter.toJsonField("fn", "attr",false);
		assertEquals("\"fn\":\"attr\"", s);
	}
	
}
