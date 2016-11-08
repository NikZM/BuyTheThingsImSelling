package com.fdmgroup.buythethingsisell.springcontrollers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import static org.mockito.Mockito.*;

import java.util.Iterator;
import java.util.Set;

import com.fdmgroup.buythethingsisell.entities.BasketEntity;
import com.fdmgroup.buythethingsisell.entities.EntityFactory;
import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemReadJDBC;

public class BasketPageTest {
	
	@InjectMocks private BasketPage basketPage;
	@Mock private ItemReadJDBC mockItemReadJDBC;
	@Mock private ItemEntity mockItemEntity;
	@Mock private BasketEntity mockBasketEntity;
	@Mock private Model mockModel;
	@Mock private Set<Integer> mockKeySet;
	@Mock private Iterator<Integer> mockIterator;
	@Mock private EntityFactory mockEntityFactory;
	@Mock private ItemsEntityWrapper mockItemEntityWrapper;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(mockItemReadJDBC.getItemByID(anyInt())).thenReturn(mockItemEntity);
		when(mockBasketEntity.keySet()).thenReturn(mockKeySet);
		when(mockKeySet.iterator()).thenReturn(mockIterator);
		when(mockIterator.hasNext()).thenReturn(true, true, true, false);
		when(mockIterator.next()).thenReturn(1,2,3);
		when(mockBasketEntity.get(anyInt())).thenReturn(mockBasketEntity.new PriceStockPair(1));
		when(mockEntityFactory.getItemEntityWrapper()).thenReturn(mockItemEntityWrapper);
	}
	
	@Test
	public void test_gotoBasketPage(){
		basketPage.gotoBasketPage(mockBasketEntity, mockModel);
		verify(mockItemEntity,times(3)).setUnitsAvailable(anyInt());
	}
	
	@Test
	public void test_updateBasket_CallsTrueRemoveIfQuantityZero(){
		int quantity = 0;
		basketPage.updateBasket(mockBasketEntity, mockModel, 1, 1, quantity);
		verify(mockBasketEntity).trueRemove(1);
	}
	
	@Test
	public void test_updateBasket_ReplacesIfQuantityIsMoreThanZero(){
		int quantity = 2;
		basketPage.updateBasket(mockBasketEntity, mockModel, 1, 1, quantity);
		verify(mockBasketEntity).replace(anyInt(), anyObject());
	}

}
