package com.fdmgroup.buythethingsisell.springcontrollers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdmgroup.buythethingsisell.entities.BasketEntity;
import com.fdmgroup.buythethingsisell.entities.ItemEntity;

import static org.mockito.Mockito.*;

public class IndexPageTest {
	
	private IndexPage indexPage;
	@Mock Model mockModel;
	@Mock ItemEntity mockItemEntity;
	@Mock BasketEntity mockBasket;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		this.indexPage = new IndexPage();
	}
	
	@Test
	public void test_getIndex_AddsCartIfOneDoesNotExist_WhenCalled(){
		when(mockModel.containsAttribute(anyString())).thenReturn(false);
		indexPage.getIndex(mockModel);
		verify(mockModel).addAttribute(anyString(),anyObject());
	}
	
	@Test
	public void test_getIndex_DoesNotAddCartIfOneAlreadyExists_WhenCalled(){
		when(mockModel.containsAttribute(anyString())).thenReturn(true);
		indexPage.getIndex(mockModel);
		verify(mockModel,never()).addAttribute(anyString(),anyObject());
	}
	
	@Test
	public void test_addProduct_PutsItemEntityIntoBasket_WhenCalled(){
		indexPage.addProduct(mockItemEntity, mockBasket);
		verify(mockBasket).put(anyInt(), anyObject());
	}
	
	@Test
	public void test_removeProduct_PutsItemEntityIntoBasket_WhenCalled(){
		indexPage.removeProduct(mockItemEntity, mockBasket);
		verify(mockBasket).remove(anyInt());
	}

}
