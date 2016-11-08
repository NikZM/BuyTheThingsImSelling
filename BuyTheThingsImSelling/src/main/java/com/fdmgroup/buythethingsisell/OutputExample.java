package com.fdmgroup.buythethingsisell;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fdmgroup.buythethingsisell.restcontrollers.RestController;

public class OutputExample {
	
	final static Logger logger = Logger.getLogger(OutputExample.class);
	
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context-copy.xml");
		
		RestController restController = (RestController) context.getBean("restController");
		
		
		Response resp = restController.getResp(1,7);
		
		String  s =(String) resp.getEntity();
		
	System.out.println(s);
	logger.debug(s);
		
	/*	ItemEntity itemEntity = new ItemEntity();
		itemEntity.setId(31);
		itemEntity.setPrice(75.0);
		BasketEntity basketEntity = new BasketEntity();
		
		basketEntity.put(itemEntity.getId(), basketEntity.new PriceStockPair(itemEntity.getPrice()));
		basketEntity.put(itemEntity.getId(), basketEntity.new PriceStockPair(itemEntity.getPrice()));
		System.out.println(basketEntity.toString());*/
		
	}
}