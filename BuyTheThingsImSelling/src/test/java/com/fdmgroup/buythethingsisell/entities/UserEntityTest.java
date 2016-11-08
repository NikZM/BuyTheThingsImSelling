package com.fdmgroup.buythethingsisell.entities;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserEntityTest {
	
	@InjectMocks private User user;
	@Mock private JSONConverter mockJsonConverter;
	@Mock private List<Integer> mockItemsRegisteredList;
	@Mock private List<Integer> mockPrevTransacsList;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		user.setUserID(1);
		user.setUserName("email");
		user.setPasswordHashed("5f4dcc3b5aa765d61d8327deb882cf99");
		user.setPaypalAcc("paypal");
		user.setDateJoined("01-11-2016");
		user.setPreviousTransactions(mockPrevTransacsList);
		user.setItemsRegistered(mockItemsRegisteredList);
	}
	
	@Test
	public void test_gettersAndSetters(){
		
		assertEquals(1, user.getUserID());
		
		assertEquals("email", user.getUserName());
		
		assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", user.getPasswordHashed());
		
		assertEquals("paypal", user.getPaypalAcc());
		
		assertEquals("01-11-2016", user.getDateJoined());
	}
	
	@Test
	public void test_unhashedToHashed(){
		user.setPasswordUnHashed("password");
		assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", user.getPasswordHashed());
	}
	
	@Test
	public void test_getPreviousTransaction_returnsGivenTransactionList_WhenCalled(){
		assertEquals(mockPrevTransacsList, user.getPreviousTransactions());
	}
	
	@Test
	public void test_getItemsRegistered_returnsGivenItemRegisteredList_WhenCalled(){
		assertEquals(mockItemsRegisteredList, user.getItemsRegistered());
	}
	
	@Test
	public void test_addTransaction_callsAddOnTransactionList_WhenCalled(){
		user.addTransactionID(1);
		verify(mockPrevTransacsList).add(1);
	}
	
	@Test
	public void test_addRegisteredItem_CallsAddOnMockRegisteredList_WhenCalled(){
		user.addRegisteredItem(1);
		verify(mockItemsRegisteredList).add(1);
	}
	
	@Test
	public void test_toString_CallsToString_WhenCalled(){
		user.toString();
		verify(mockJsonConverter,times(3)).toJsonField(anyString(), anyString());
		verify(mockJsonConverter).toJsonField(anyString(), anyInt());
		verify(mockJsonConverter).toJsonField("previousTransactions", mockPrevTransacsList);
		verify(mockJsonConverter).toJsonField("itemsRegistered", mockItemsRegisteredList);
	}

}
