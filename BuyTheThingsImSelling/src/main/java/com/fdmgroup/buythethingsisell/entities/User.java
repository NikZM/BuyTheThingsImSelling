package com.fdmgroup.buythethingsisell.entities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.fdmgroup.buythethingsisell.jdbc.userfunctions.MD5Hasher;

public class User {

	@Resource
	private JSONConverter jc;
	
	private int userID;
	private String userName;
	private String passwordHashed;
	private String paypalAcc;
	private String dateJoined;
	private List<Integer> itemsRegistered = new ArrayList<Integer>();
	private List<Integer> previousTransactions = new ArrayList<Integer>();

	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasswordHashed() {
		return passwordHashed;
	}
	public void setPasswordHashed(String passwordHashed) {
		this.passwordHashed = passwordHashed;
	}
	public void setPasswordUnHashed(String passwordUnHashed){
		this.passwordHashed = MD5Hasher.getMD5Hashed(passwordUnHashed);
	}
	public String getPaypalAcc() {
		return paypalAcc;
	}
	public void setPaypalAcc(String paypalAcc) {
		this.paypalAcc = paypalAcc;
	}
	public String getDateJoined() {
		return dateJoined;
	}
	public void setDateJoined(String dateJoined) {
		this.dateJoined = dateJoined;
	}
	public List<Integer> getItemsRegistered() {
		return itemsRegistered;
	}
	public void setItemsRegistered(List<Integer> itemsRegistered) {
		this.itemsRegistered = itemsRegistered;
	}
	public void addRegisteredItem(int id) {
		itemsRegistered.add(id);
	}
	public List<Integer> getPreviousTransactions() {
		return previousTransactions;
	}
	public void setPreviousTransactions(List<Integer> previousTransactions) {
		this.previousTransactions = previousTransactions;
	}
	
	public void addTransactionID(int id) {
		previousTransactions.add(id);
	}
	
	@Override
	public String toString(){
		String str = "{" + jc.toJsonField("userID", userID) + ", " + jc.toJsonField("username", userName) + "," + 
				jc.toJsonField("password", passwordHashed) + ", " + jc.toJsonField("dateJoined", dateJoined) + "," +
				jc.toJsonField("itemsRegistered", itemsRegistered) + "," + jc.toJsonField("previousTransactions", previousTransactions) +
				"}";
		return str;
	}
}
