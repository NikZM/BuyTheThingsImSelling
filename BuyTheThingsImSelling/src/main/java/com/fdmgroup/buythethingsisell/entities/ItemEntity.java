package com.fdmgroup.buythethingsisell.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemEntity implements Serializable{

	private static final long serialVersionUID = 8142619469962013907L;
	private int id;
	private String title;
	private String description;
	private double price;
	private String imgURL;
	private int unitsAvailable;
	private String sellerEmail;
	private int reviewCount;
	private int avgReviewScore;
	private List<ReviewEntity> reviews;
	private List<String> categories;
	private JSONConverter jc;

	public ItemEntity() {
		this.reviews = new ArrayList<ReviewEntity>();
		this.categories = new ArrayList<String>();
	}
	
	public ItemEntity(JSONConverter jsonConverter) {
		this.reviews = new ArrayList<ReviewEntity>();
		this.categories = new ArrayList<String>();
		this.jc = jsonConverter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		if (description != null){
			return description;
		} else {
			return "No description for this item...";
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public int getUnitsAvailable() {
		return unitsAvailable;
	}

	public void setUnitsAvailable(int unitsAvailable) {
		this.unitsAvailable = unitsAvailable;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public int getAvgReviewScore() {
		return avgReviewScore;
	}

	public void setAvgReviewScore(int avgReviewScore) {
		this.avgReviewScore = avgReviewScore;
	}

	public List<ReviewEntity> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewEntity> reviews) {
		this.reviews = reviews;
	}
	
	public void addReview(ReviewEntity revEnt){
		reviews.add(revEnt);
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	public void setCategories(String csv) {
		List<String> categories = Arrays.asList(csv.split(","));
		this.categories = categories;
	}
	
	@Override
	public String toString(){
		String c = ",";
		String str = "{" + jc.toJsonField("id", id) + c + jc.toJsonField("title", title) + c + 
				jc.toJsonField("description", getDescription()) + c + jc.toJsonField("price", String.valueOf(price)) + c +
				jc.toJsonField("imgURL", imgURL) + c + jc.toJsonField("unitsAvailable", unitsAvailable) + c +
				jc.toJsonField("sellerEmail", sellerEmail) + c + jc.toJsonField("reviewCount", reviewCount) + c +
				jc.toJsonField("avgScore", avgReviewScore) + c + jc.toJsonField("reviews", (reviews.toString()), true) + c + 
				jc.toJsonField("categories", categories) +"}";
		return str;
	}

}