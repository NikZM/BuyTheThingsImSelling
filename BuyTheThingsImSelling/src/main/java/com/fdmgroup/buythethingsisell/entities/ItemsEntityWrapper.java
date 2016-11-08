package com.fdmgroup.buythethingsisell.entities;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

public class ItemsEntityWrapper {
	
	@Resource
	private JSONConverter jc;
	
	private List<ItemEntity> items = new ArrayList<ItemEntity>();
	private int pageNumber;
	private int pageMax;
	
	public ItemsEntityWrapper(JSONConverter jsonConverter) {
		this.jc = jsonConverter;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public void setPageMax(int pageMax) {
		this.pageMax = pageMax;
	}
	
	public void add(ItemEntity ie){
		items.add(ie);
	}
	
	public List<ItemEntity> getItems(){
		return items;
	}
	
	@Override
	public String toString(){
		StringBuffer strB = new StringBuffer("{\"items\":[");
		for(ItemEntity item : items){
			strB.append(item.toString() + ", ");
		}
		if(items.size() != 0){
			strB.deleteCharAt(strB.length()-2);
		}
		strB.append("]," + jc.toJsonField("page", pageNumber) + "," + jc.toJsonField("pageMax", pageMax) + "}");
		return strB.toString();
	}

}