package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fdmgroup.buythethingsisell.entities.EntityFactory;
import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.entities.ReviewEntity;

import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ItemEntityBuilderTest {
	
	@InjectMocks private ItemEntityBuilder itemEntityBuilder;
	@Mock private ResultSet res;
	@Mock private ResultSet resCat;
	@Mock private ResultSet resRev;
	@Mock private Blob blob;
	@Mock private EntityFactory mockEntityFactory;
	@Mock private ItemEntity mockEntity;
	@Mock private ItemsEntityWrapper mockEntityWrapper;
	@Mock private ReviewEntity mockReviewEntity;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		byte[] bytearr = "description".getBytes();
		when(res.next()).thenReturn(true);
		when(res.getInt(1)).thenReturn(1);
		when(res.getString(2)).thenReturn("title");
		when(res.getBlob(3)).thenReturn(blob);
		when(blob.getBytes(anyInt(), anyInt())).thenReturn(bytearr);
		when(res.getDouble(4)).thenReturn(10.0);
		when(res.getString(5)).thenReturn("http://imgURL/");
		when(res.getInt(6)).thenReturn(10);
		when(res.getString(7)).thenReturn("seller@bttis.com");
		when(resCat.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(resCat.getString(1)).thenReturn("cat1").thenReturn("cat2");
		when(mockEntityFactory.getItemEntity()).thenReturn(mockEntity);
		when(mockEntityFactory.getItemEntityWrapper()).thenReturn(mockEntityWrapper);
		when(mockEntityFactory.getReviewEntity()).thenReturn(mockReviewEntity);
	}

	@Test
	public void test() throws SQLException {
		itemEntityBuilder.getItemByID(res, resCat, resRev);
		verify(res,times(1)).next();
	}
	@Test
	public void test2() throws SQLException {
		when(res.getBlob(3)).thenReturn(null);
		itemEntityBuilder.getItemByID(res, resCat, resRev);
		verify(res,times(1)).next();
	}
	@Test
	public void test3() throws SQLException {
		when(res.next()).thenReturn(false);
		itemEntityBuilder.getItemByID(res, resCat, resRev);
		verify(res,never()).getInt(anyInt());
	}
	@Test
	public void test4() throws SQLException {
		when(res.next()).thenReturn(true, true, false);
		itemEntityBuilder.getItemsByPage(res);
		verify(res,times(3)).next();
	}
	@Test
	public void test5() throws SQLException {
		when(res.next()).thenReturn(false);
		itemEntityBuilder.getItemsByPage(res);
		verify(res).next();
	}
	@Test
	public void test6() throws SQLException {
		when(res.getBlob(3)).thenReturn(null);
		when(res.next()).thenReturn(true, true, false);
		itemEntityBuilder.getItemsByPage(res);
		verify(blob,never()).getBytes(anyInt(), anyInt());
	}
	@Test
	public void test7() throws SQLException {
		when(resRev.next()).thenReturn(true, true, false);
		when(resRev.getInt(1)).thenReturn(1);
		when(resRev.getString(2)).thenReturn("buyer@bttis.com");
		when(resRev.getString(3)).thenReturn("comment");
		when(resRev.getInt(4)).thenReturn(2);
		itemEntityBuilder.getItemByID(res, resCat, resRev);
		verify(resRev,times(3)).next();
	}

}
