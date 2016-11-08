package com.fdmgroup.buythethingsisell.entities;

import javax.annotation.Resource;

public class ReviewEntity {
	
	@Resource
	private JSONConverter jc;
	
	private int reviewID;
	private String userEmail;
	private String comment;
	private int rating;
	
	public ReviewEntity(JSONConverter jsonConverter) {
		this.jc = jsonConverter;
	}
	public int getReviewID() {
		return reviewID;
	}
	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	@Override
	public String toString(){
		return "{" + jc.toJsonField("reviewID", reviewID) + "," + jc.toJsonField("userEmail", userEmail) + "," + 
				jc.toJsonField("comment", comment) + "," + jc.toJsonField("rating", rating) + "}";
	}

}
