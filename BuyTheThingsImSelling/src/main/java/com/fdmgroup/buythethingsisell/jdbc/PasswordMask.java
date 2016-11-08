package com.fdmgroup.buythethingsisell.jdbc;

public class PasswordMask {
	
	public static String getPassword(){
		int[] byteArr = {79, 99, 50, 99, 48, 98, 102, 52};
		char[] charArr = new char[byteArr.length];
		for(int i=0; i <byteArr.length; i++){
			charArr[i] = Character.toChars(byteArr[i])[0];
		}
		return String.valueOf(String.copyValueOf(charArr));
	}
}
