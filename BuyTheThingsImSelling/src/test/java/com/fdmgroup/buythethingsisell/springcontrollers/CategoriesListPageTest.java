package com.fdmgroup.buythethingsisell.springcontrollers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;

import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemByCategoryJDBC;

public class CategoriesListPageTest {
	
	@InjectMocks private CategoriesListPage categoriesListPage;
	@Mock private ItemByCategoryJDBC mockItemByCategoryJDBC;
	@Mock private ItemsEntityWrapper mockItemEntityWrapper;
	@Mock private Model mockModel;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(mockItemByCategoryJDBC.getItemsByCategory(anyString())).thenReturn(mockItemEntityWrapper);
	}

	@Test
	public void test_GoToFilteredCategoryPage_ReplacesAllWithPercent(){
		String cat = "all";
		categoriesListPage.gotoFilteredCategoryPage(mockModel, cat);
		verify(mockItemByCategoryJDBC).getItemsByCategory("%");
	}
	
	@Test
	public void test_GoToFilteredCategoryPage_CallsGetItemsByCatWithCategory(){
		String cat = "Cat1";
		categoriesListPage.gotoFilteredCategoryPage(mockModel, cat);
		verify(mockItemByCategoryJDBC).getItemsByCategory(cat);
	}
}
