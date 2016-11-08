package com.fdmgroup.buythethingsisell.springcontrollers;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.DeleteItemJDBC;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemBySellerNameJDBC;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemReadJDBC;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemWriteJDBC;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.UpdateItemJDBC;

public class SellerPageTest {
	
	@InjectMocks private SellersPage sellersPage;
	@Mock private ItemReadJDBC mockItemReadJDBC;
	@Mock private ItemWriteJDBC mockItemWriteJDBC;
	@Mock private UpdateItemJDBC mockUpdateItemJDBC;
	@Mock private DeleteItemJDBC mockDeleteItemJDBC;
	@Mock private ItemBySellerNameJDBC mockItemBySellerNameJDBC;
	@Mock private ItemsEntityWrapper mockItemEntityWrapper;
	@Mock private ModelMap mockModelMap;
	@Mock private SecurityContextUserName mockSecurityContextUserName;
	@Mock private ModelAndView mockModelAndView;
	@Mock private ItemEntity mockItemEntity;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(mockItemBySellerNameJDBC.getItemsBySeller(anyString())).thenReturn(mockItemEntityWrapper);
		when(mockSecurityContextUserName.getUserName()).thenReturn("email1");
		when(mockItemReadJDBC.getItemByID(anyInt())).thenReturn(mockItemEntity);
		when(mockItemEntity.getId()).thenReturn(0);
		when(mockItemWriteJDBC.postItem(mockItemEntity, "email1")).thenReturn(true);
		when(mockDeleteItemJDBC.deleteItem(anyInt())).thenReturn(true);
	}
	
	@Test
	public void test_getSellerPage_GetItemsBySellerEmail_WhenCalled(){
		sellersPage.getSellPage(mockModelMap);
		verify(mockItemBySellerNameJDBC).getItemsBySeller("email1");
	}
	
	@Test
	public void test_getSellerPage_addsItemEntitiesToModelAndMap_WhenCalled(){
		sellersPage.getSellPage(mockModelMap);
		verify(mockModelMap).addAttribute("prevItems", mockItemEntityWrapper.getItems());
	}
	
	@Test
	public void test_getSellerPage_DoesNotGetItemsBySeller_WhenCalledWithNoUserName(){
		when(mockSecurityContextUserName.getUserName()).thenReturn(null);
		sellersPage.getSellPage(mockModelMap);
		verify(mockItemBySellerNameJDBC,never()).getItemsBySeller(anyString());
	}
	
	@Test
	public void test_getNewItem_AddsExistingEntityToModel_WhenEntityiDIsPresent(){
		sellersPage.getNewItem(mockModelAndView, 1);
		verify(mockModelAndView).addObject("itemEntity", mockItemEntity);
	}
	
	@Test
	public void test_getNewItem_AddsNewEntityToModel_WhenNoIdeaPresent(){
		sellersPage.getNewItem(mockModelAndView, null);
		verify(mockModelAndView).addObject(eq("itemEntity"), anyObject());
	}
	
	@Test
	public void test_submitNewItem_AddsSubmittedAddtributeToMap_WhenSuccessfullyCalled(){
		sellersPage.submitNewItem(mockModelMap, mockItemEntity);
		verify(mockModelMap).addAttribute("submitted", true);
	}
	
	@Test
	public void test_submitNewItem_callsPostItem_WhenItemIsNew(){
		sellersPage.submitNewItem(mockModelMap, mockItemEntity);
		verify(mockItemWriteJDBC).postItem(mockItemEntity, "email1");
	}
	
	@Test
	public void test_submitNewItem_CallsUpdateItem_WhenItemAlreadyExists(){
		when(mockItemEntity.getId()).thenReturn(10);
		sellersPage.submitNewItem(mockModelMap, mockItemEntity);
		verify(mockUpdateItemJDBC).updateItem(mockItemEntity);
	}
	
	@Test
	public void test_submitNewItem_ItemIsNotPostedAndSubmittedIsAddedAsFalse_WhenNoUserNamePresent(){
		when(mockSecurityContextUserName.getUserName()).thenReturn(null);
		sellersPage.submitNewItem(mockModelMap, mockItemEntity);
		verify(mockItemWriteJDBC,never()).postItem(eq(mockItemEntity), anyString());
		verify(mockModelMap).addAttribute("submitted", false);
	}
	
	@Test
	public void test_deleteItem_callsDeleteItemAndAddsSubmittedAttributeAsTrue_WhenCalledSuccessfully(){
		sellersPage.deleteItem(mockModelMap, mockItemEntity);
		verify(mockDeleteItemJDBC).deleteItem(anyInt());
		verify(mockModelMap).addAttribute("submitted", true);
	}

}
