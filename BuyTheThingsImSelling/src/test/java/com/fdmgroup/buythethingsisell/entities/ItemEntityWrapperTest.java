package com.fdmgroup.buythethingsisell.entities;
import static org.mockito.Mockito.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ItemEntityWrapperTest {
	
	@InjectMocks private ItemsEntityWrapper itemEntityWrapper;
	@Mock private List<ItemEntity> mockItemEntList;
	@Mock private Iterator<ItemEntity> mockIterator;
	@Mock private ItemEntity mockItemEntity;
	@Mock private JSONConverter mockJSONConverter;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(mockItemEntList.iterator()).thenReturn(mockIterator);
		when(mockIterator.hasNext()).thenReturn(true, false);
		when(mockIterator.next()).thenReturn(mockItemEntity);
		when(mockItemEntity.toString()).thenReturn("{\"mockJSONString\":\"true\"}");
		when(mockJSONConverter.toJsonField(anyString(), anyInt())).thenReturn("{\"mockJSONString\":\"true\"}");
		when(mockItemEntList.size()).thenReturn(1);
		itemEntityWrapper.setPageNumber(1);
		itemEntityWrapper.setPageMax(3);
	}
	
	@Test
	public void test_PageNumber_ReturnsSetPageNumber(){
		itemEntityWrapper.toString();
		verify(mockJSONConverter).toJsonField("page", 1);
	}
	
	@Test
	public void test_PageMax_ReturnsJSONWithPageMax(){
		itemEntityWrapper.toString();
		verify(mockJSONConverter).toJsonField("pageMax", 3);
		
	}
	
	@Test
	public void toString_(){
		itemEntityWrapper.toString();
		verify(mockJSONConverter,times(2)).toJsonField(anyString(), anyInt());
	}
	

}
