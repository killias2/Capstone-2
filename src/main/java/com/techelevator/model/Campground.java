package com.techelevator.model;

public class Campground extends Park{
	
	private int campgroundId;
	private String campName;
	private String openFromMonth;
	private String openToMonth;
	private int dailyFee;
	
	public int getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	public String getCampName() {
		return campName;
	}
	public void setCampName(String name) {
		this.campName = name;
	}
	public String getOpenFromMonth() {
		return openFromMonth;
	}
	public void setOpenFromMonth(String openFromMonth) {
		this.openFromMonth = openFromMonth;
	}
	public String getOpenToMonth() {
		return openToMonth;
	}
	public void setOpenToMonth(String openToMonth) {
		this.openToMonth = openToMonth;
	}
	public int getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(int dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	
	

}
