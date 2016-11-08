package com.fdmgroup.buythethingsisell.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
public class ReviewEntityTest {
	
	@InjectMocks private ReviewEntity reviewEntity;
	@Mock JSONConverter mockJSONConverter;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		reviewEntity.setReviewID(1);
		reviewEntity.setUserEmail("email");
		reviewEntity.setComment("comment");
		reviewEntity.setRating(10);
		
	}
	
	@Test
	public void test_gettersAndSetters(){
		
		assertEquals(1, reviewEntity.getReviewID());
		
		assertEquals("email", reviewEntity.getUserEmail());
		
		assertEquals("comment", reviewEntity.getComment());
		
		assertEquals(10, reviewEntity.getRating());
		
	}
	
	@Test
	public void test_toString_CallsToJsonFieldFourTimes(){
		reviewEntity.toString();
		verify(mockJSONConverter,times(2)).toJsonField(anyString(), anyString());
		verify(mockJSONConverter,times(2)).toJsonField(anyString(), anyInt());
	}

}
