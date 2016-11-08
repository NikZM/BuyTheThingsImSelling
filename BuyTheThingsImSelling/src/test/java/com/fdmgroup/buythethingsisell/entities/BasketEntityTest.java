package com.fdmgroup.buythethingsisell.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.fdmgroup.buythethingsisell.entities.BasketEntity.PriceStockPair;

public class BasketEntityTest {
	
	@InjectMocks private BasketEntity basketEntity;
	@Mock private JSONConverter mockJSONConverter;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(mockJSONConverter.toJsonField(anyString(), anyInt())).thenReturn("anyString:anyInt");
	}
	
	
	
	@Test
	public void test_Put_PriceStockPairIsReturnedIsSameAsEntered(){
		PriceStockPair psp = basketEntity.new PriceStockPair(12.99);
		basketEntity.put(1, psp);
		PriceStockPair actual = basketEntity.get(1);
		assertEquals(psp, actual);
	}
	
	@Test
	public void test_getPriceTotal(){
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(2, basketEntity.new PriceStockPair(7.99));
		double expected = 33.97;
		double actual = basketEntity.getPriceTotal();
		assertEquals(expected, actual, 0);
	}
	
	@Test
	public void test_getPriceTotalAsString_ReturnsSterlingFormatString(){
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(2, basketEntity.new PriceStockPair(7.99));
		String expected = "£33.97";
		String actual = basketEntity.getPriceTotalS();
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_getPriceTotalAsString_ReturnsZeroWithNoFormat_WhenPriceIsZero(){
		String expected = "0";
		String actual = basketEntity.getPriceTotalS();
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_Remove_CompleteRemovesItemIFQuantityIsOneOrLess(){
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.remove(1);
		assertFalse(basketEntity.containsKey(1));
	}
	
	@Test
	public void test_Remove_DeincrementsQuantityIfQuantityIsGreaterThanOne(){
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.remove(1);
		assertEquals(1,basketEntity.get(1).amount);
	}
	
	@Test
	public void test_Remove_DeincrementsQuantityToTwo_WhenGivenThreeAndOneIsRemoved(){
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.remove(1);
		assertEquals(2,basketEntity.get(1).amount);
	}
	
	@Test
	public void test_TrueRemove_RemovesItemRegardlessOfQuantity(){
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.trueRemove(1);
		assertFalse(basketEntity.containsKey(1));
	}
	
	@Test
	public void test_toString_CallsJsonConverter4Times_WhenCalledWithGivenInputs(){
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(1, basketEntity.new PriceStockPair(12.99));
		basketEntity.put(7, basketEntity.new PriceStockPair(10.99, 3));
		basketEntity.toString();
		verify(mockJSONConverter,times(4)).toJsonField(anyString(), anyInt());
	}

}
