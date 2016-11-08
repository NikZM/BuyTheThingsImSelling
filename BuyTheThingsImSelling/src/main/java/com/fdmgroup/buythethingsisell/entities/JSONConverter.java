package com.fdmgroup.buythethingsisell.entities;

import java.util.List;

import org.apache.log4j.Logger;

public class JSONConverter {
	
	final static Logger logger = Logger.getLogger(JSONConverter.class);
	
	public <E> String toJsonField(String fieldName, List<E> attributes) {
		StringBuilder strB = new StringBuilder("\"" + fieldName + "\":[");
		try {
			if(attributes.size() != 0 && attributes.get(0).getClass() == Class.forName("java.lang.Integer")){
				for (Object temp : attributes) {
					strB.append("" + temp + "" + ",");
				}
				if (attributes != null && attributes.size() != 0){
					strB.deleteCharAt(strB.length() - 1);
				}
			} else {
				for (Object temp : attributes) {
					strB.append("\"" + temp + "\"" + ",");
				}
				if (attributes != null && attributes.size() != 0){
					strB.deleteCharAt(strB.length() - 1);
				}
			}
		} catch (ClassNotFoundException e) {
			logger.error("No Class found - java.lang.Integer");
		}
		strB.append("]");
		return strB.toString();
	}

	public String toJsonField(String fieldName, String attribute) {
		String str = "\"" + fieldName + "\":\"" + attribute + "\"";
		return str;
	}
	
	public String toJsonField(String fieldName, String attribute, boolean noQuotes) {
		if (noQuotes){
			String str = "\"" + fieldName + "\":" + attribute + "";
			return str;
		} else {
			return toJsonField(fieldName, attribute);
		}
	}
	
	public String toJsonField(String fieldName, int attribute) {
		String str = "\"" + fieldName + "\":" + attribute;
		return str;
	}
}