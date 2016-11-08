package com.fdmgroup.buythethingsisell.restcontrollers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import javax.ws.rs.core.Response;

import com.fdmgroup.buythethingsisell.entities.EntityFactory;
import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.entities.JSONConverter;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.CategoryReaderJDBC;
import com.fdmgroup.buythethingsisell.jdbc.itemfunctions.ItemReadJDBC;
import com.fdmgroup.buythethingsisell.jdbc.userfunctions.AdminSQLDirectInterface;

public class RestControllerTest {
	
	@InjectMocks RestController restController;
	@Mock CategoryReaderJDBC mockCategoryReaderJDBC;
	@Mock AdminSQLDirectInterface mockAdminSQLDirectInterface;
	@Mock ItemReadJDBC mockItemReadJDBC;
	@Mock ItemEntity mockItemEntity;
	@Mock ItemsEntityWrapper mockItemEntityWrapper;
	@Mock JSONConverter mockJSONConverter;
	@Mock EntityFactory mockEntityFactory;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		when(mockItemReadJDBC.getItemByID(anyInt())).thenReturn(mockItemEntity);
		when(mockItemReadJDBC.getItemsByPage(anyInt(), anyInt(), anyInt())).thenReturn(mockItemEntityWrapper);
		when(mockItemEntity.toString()).thenReturn("{JSON String \r\n NewLine}");
		when(mockItemEntityWrapper.toString()).thenReturn("{JSON String \r\n NewLine}");
		when(mockJSONConverter.toJsonField(anyString(), anyInt())).thenReturn("\"anyString\":anyInt");
		when(mockEntityFactory.getItemEntityWrapper()).thenReturn(mockItemEntityWrapper);
	
	}
	
	@Test
	public void test_GetResp_CallsGetItemById_WhenCalled(){
		restController.getResp(1);
		verify(mockItemReadJDBC).getItemByID(1);
	}
	
	@Test
	public void test_GetResp_ChangesNewLineCharacterAndCarriageReturnToBreak_WhenCalled(){
		Response resp = restController.getResp(1);
		String str = (String) resp.getEntity();
		assertEquals("{JSON String <br /> NewLine}", str );
	}
	
	@Test
	public void test_getResp_convertsIntoLowZeroHighSixAndPageOne_WhenCalledWithPageNumOneAndViewCountSeven(){
		restController.getResp(1, 7);
		verify(mockItemReadJDBC).getItemsByPage(0, 6, 1);
	}
	
	@Test
	public void test_getCategories_callsGetCategories_WhenCalled(){
		restController.getCats();
		verify(mockCategoryReaderJDBC).getAllCategories();
	}
	
	@Test
	public void test_sqlDI_ExecutesQuery_WhenCalledWithQuery() throws SQLException{
		restController.sqlDI("SELECT * FROM TABLE");
		verify(mockAdminSQLDirectInterface).executeQuery("SELECT * FROM TABLE");
	}
	
	@Test
	public void test_sqlDI_ReturnsMessage_WhenExceptionIsThrown() throws SQLException{
		SQLException mockException = mock(SQLException.class);
		doThrow(mockException).when(mockAdminSQLDirectInterface).executeQuery("SELECT * FROM TABLE");
		restController.sqlDI("SELECT * FROM TABLE");
		verify(mockException).getMessage();
	}
	
	@Test
	public void test_getBasketByIds_getItemByIDIsCalledThreeTimes_WhenGivenStringOfThreeIntegers(){
		restController.getBasketItemsByID("12,2,1");
		verify(mockItemReadJDBC,times(3)).getItemByID(anyInt());
	}
	
	@Test
	public void test_getBasketByIds_StringWithNonNumericalCharactersCausesException_WhenCalled(){
		restController.getBasketItemsByID("12,2,x");
	}

}
