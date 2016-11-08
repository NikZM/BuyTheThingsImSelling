package com.fdmgroup.buythethingsisell.entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.StringJoiner;

import javax.annotation.Resource;

public class BasketEntity extends HashMap<Integer, BasketEntity.PriceStockPair> implements Serializable{
	
	@Resource
	private JSONConverter jc;

	private static final long serialVersionUID = 2L;
	private static DecimalFormat df2 = new DecimalFormat(".##");
	
	public class PriceStockPair implements Serializable{
		
		private static final long serialVersionUID = 1L;
		private double price;
		public int amount = 1;
		
		public PriceStockPair(double price) {
			this.price = price;
		}
		
		public PriceStockPair(double price, int amount) {
			this.price = price;
			this.amount = amount;
		}
	}
	
	public double getPriceTotal(){
		double d = 0;
		for(int temp : this.keySet()){
			d += (this.get(temp).price * this.get(temp).amount);
		} return d;
	}
	
	public String getPriceTotalS(){
		double d = getPriceTotal();
		if (d == 0.0){
			return "0";
		} else {
			String s = "£" + df2.format(d);
			return s;
		}
	}

	@Override 
	public PriceStockPair put(Integer key, PriceStockPair value) {
		if (this.containsKey(key)){
			PriceStockPair psp = this.get(key);
			psp.amount ++;
			super.remove(key);
			super.put(key, psp);
		} else {
			super.put(key, value);
		}
		return value;
	};
	
	@Override
	public PriceStockPair remove(Object key){
		PriceStockPair psp = this.get(key);
		if (psp.amount > 1){
			psp.amount --;
			super.remove(key);
			super.put((Integer) key, psp);
		} else {
			super.remove(key);
		}
		return psp;
	}
	
	public PriceStockPair trueRemove(Object key){
		return super.remove(key);
	}
	
	@Override
	public String toString(){
		StringJoiner stringJoiner = new StringJoiner(",");
		for(int i : this.keySet()){
			String s = "{" + jc.toJsonField("itemID", i) + ", " + (jc.toJsonField("amount", this.get(i).amount)) + "}";
			stringJoiner.add(s);
		}
		return "[" + stringJoiner.toString() + "]";
	}
}