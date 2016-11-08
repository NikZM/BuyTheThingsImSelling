package com.fdmgroup.buythethingsisell.jdbc.itemfunctions;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.fdmgroup.buythethingsisell.entities.EntityFactory;
import com.fdmgroup.buythethingsisell.entities.ItemEntity;
import com.fdmgroup.buythethingsisell.entities.ItemsEntityWrapper;
import com.fdmgroup.buythethingsisell.entities.ReviewEntity;

public class ItemEntityBuilder {

	final static Logger logger = Logger.getLogger(ItemEntityBuilder.class);
	
	@Resource
	private EntityFactory entityFactory;

	public ItemEntity getItemByID(ResultSet res, ResultSet resCat, ResultSet resRev) throws SQLException {
		ItemEntity itemEntity = entityFactory.getItemEntity();
		if (res.next()) {
			itemEntity.setId(res.getInt(1));
			itemEntity.setTitle(res.getString(2));
			Blob descriptBlob = res.getBlob(3);
			if (descriptBlob != null) {
				itemEntity.setDescription(new String(descriptBlob.getBytes(1, (int) descriptBlob.length())));
			}
			itemEntity.setPrice(res.getDouble(4));
			itemEntity.setImgURL(res.getString(5));
			itemEntity.setUnitsAvailable(res.getInt(6));
			itemEntity.setSellerEmail(res.getString(7));

			while (resCat.next()) {
				itemEntity.getCategories().add(resCat.getString(1));
			}

			int reviewCount = 0;
			int reviewScore = 0;
			while (resRev.next()) {
				ReviewEntity reviewEntity = entityFactory.getReviewEntity();
				reviewEntity.setReviewID(resRev.getInt(1));
				reviewEntity.setUserEmail(resRev.getString(2));
				reviewEntity.setComment(resRev.getString(3));
				reviewEntity.setRating(resRev.getInt(4));
				itemEntity.addReview(reviewEntity);
				reviewScore += resRev.getInt(4);
				reviewCount++;
			}
			itemEntity.setReviewCount(reviewCount);
			if (reviewCount > 0) {
				itemEntity.setAvgReviewScore((reviewScore / reviewCount));
			}
		}
		return itemEntity;
	}

	public ItemsEntityWrapper getItemsByPage(ResultSet res) throws SQLException {
		ItemsEntityWrapper iew = entityFactory.getItemEntityWrapper();
		while (res.next()) {
			ItemEntity itemEntity = entityFactory.getItemEntity();
			itemEntity.setId(res.getInt(1));
			itemEntity.setTitle(res.getString(2));
			Blob descriptBlob = res.getBlob(3);
			if (descriptBlob != null) {
				itemEntity.setDescription(new String(descriptBlob.getBytes(1, (int) descriptBlob.length())));
			}
			itemEntity.setPrice(res.getDouble(4));
			itemEntity.setImgURL(res.getString(5));
			itemEntity.setUnitsAvailable(res.getInt(6));
			itemEntity.setSellerEmail(res.getString(7));
			itemEntity.setAvgReviewScore(res.getInt(8));
			itemEntity.setReviewCount(res.getInt(9));
			iew.add(itemEntity);
		}
		return iew;
	}
}