package com.fdmgroup.buythethingsisell.springcontrollers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemReadJDBC;

public class ProductPageTest {
	
	@InjectMocks private ProductPage productPage;
	@Mock private ItemReadJDBC mockItemReadJDBC;
	@Mock private ItemEntity mockItemEntity;
	@Mock private Model mockModel;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(mockItemReadJDBC.getItemByID(anyInt())).thenReturn(mockItemEntity);
	}
	
	@Test
	public void test_gotoProductPage_ReturnsProductPage_WhenCalled(){
		String expected = "productPage";
		String actual = productPage.gotoProductPage(mockModel, 1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_gotoProductPage_AddItemEntityToModel_WhenCalled(){
		productPage.gotoProductPage(mockModel, 1);
		verify(mockModel).addAttribute("item",mockItemEntity);
	}

}
