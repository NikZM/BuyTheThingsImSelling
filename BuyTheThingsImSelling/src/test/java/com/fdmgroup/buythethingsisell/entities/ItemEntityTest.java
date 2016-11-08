package com.fdmgroup.buythethingsisell.entities;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ItemEntityTest {

	@InjectMocks private ItemEntity itemEntity;
	@Mock private JSONConverter mockJSONConverter;
	@Mock private List<ReviewEntity> expectedReviewEntList;
	@Mock private List<String> expectedStringList;
	@Mock private ReviewEntity expectedReviewEntity;
	@Mock private Iterator<ReviewEntity> mockReviewEntIt;
	@Mock private Iterator<String> mockIterator;
	private int expectedInts = 1;
	private double expectedDouble = 1.0;
	private String expectedString = "A String";
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(expectedStringList.iterator()).thenReturn(mockIterator);
		when(mockIterator.hasNext()).thenReturn(true, true, true, false);
		when(mockIterator.next()).thenReturn("ex1","ex2","ex3");
		when(expectedReviewEntList.iterator()).thenReturn(mockReviewEntIt);
		when(mockReviewEntIt.hasNext()).thenReturn(false);
		itemEntity.setId(expectedInts);
		itemEntity.setTitle(expectedString);
		itemEntity.setDescription(expectedString);
		itemEntity.setPrice(expectedDouble);
		itemEntity.setImgURL(expectedString);
		itemEntity.setUnitsAvailable(expectedInts);
		itemEntity.setSellerEmail(expectedString);
		itemEntity.setReviewCount(expectedInts);
		itemEntity.setAvgReviewScore(expectedInts);
		itemEntity.setReviews(expectedReviewEntList);
		itemEntity.setCategories(expectedStringList);
	}
	
	@Test
	public void test_settersAndGetters(){
		
		assertEquals(expectedInts, itemEntity.getId());
		
		assertEquals(expectedString, itemEntity.getTitle());
	
		assertEquals(expectedString, itemEntity.getDescription());
		
		assertEquals(expectedDouble, itemEntity.getPrice(),0);
		
		assertEquals(expectedString, itemEntity.getImgURL());
		
		assertEquals(expectedInts, itemEntity.getUnitsAvailable());
		
		assertEquals(expectedString, itemEntity.getSellerEmail());
	
		assertEquals(expectedInts, itemEntity.getReviewCount());
		
		assertEquals(expectedInts, itemEntity.getAvgReviewScore());
		
		assertEquals(expectedReviewEntList, itemEntity.getReviews());
		
		expectedReviewEntList.add(expectedReviewEntity);
		itemEntity.addReview(expectedReviewEntity);
		
		assertEquals(expectedReviewEntList, itemEntity.getReviews());
		
		assertEquals(expectedStringList, itemEntity.getCategories());
	}
	
	@Test
	public void test_addCategories_createsArrayList_WhenGivenCSVs(){
		String categories = "cat1,cat2,cat3";
		@SuppressWarnings("serial")
		List<String> categoriesList = new ArrayList<String>(){{add("cat1"); add("cat2"); add("cat3");}};
		itemEntity.setCategories(categories);
		assertEquals(categoriesList, itemEntity.getCategories());
	}
	
	@Test
	public void test_getDescription_returnsNoDescriptionFound_WhenDescriptionIsNull(){
		itemEntity.setDescription(null);
		assertEquals("No description for this item...", itemEntity.getDescription());
	}
	
	@Test
	public void test_toString_ReturnJSONFormatString(){
		itemEntity.toString();
		verify(mockJSONConverter,times(5)).toJsonField(anyString(), anyString());
		verify(mockJSONConverter,times(4)).toJsonField(anyString(), anyInt());
	}
}
