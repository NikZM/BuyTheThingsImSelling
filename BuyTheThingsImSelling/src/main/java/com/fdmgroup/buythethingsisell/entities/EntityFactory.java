package com.fdmgroup.buythethingsisell.entities;

import javax.annotation.Resource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class EntityFactory {
	
	@Resource
	private JSONConverter jsonConverter;
	
	public ItemEntity getItemEntity(){
		return new ItemEntity(jsonConverter);
	}

	public ItemsEntityWrapper getItemEntityWrapper() {
		return new ItemsEntityWrapper(jsonConverter);
	}

	public ReviewEntity getReviewEntity() {
		return new ReviewEntity(jsonConverter);
	}

	public ComboPooledDataSource getComboPooledDataSource() {
		return new ComboPooledDataSource();
	}
	
	public BasketEntity getBasketEntity(){
		return new BasketEntity(jsonConverter);
	}

}
