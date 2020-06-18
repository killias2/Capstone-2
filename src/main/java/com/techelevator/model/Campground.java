package com.techelevator.model;

public class Campground {
	
	private int campgroundId;
	private int park_Id;
	private String name;
	private int openFromMonth;
	private int openToMonth;
	private int dailyFee;
	
	public int getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getPark_Id() {
		return park_Id;
	}
	public void setPark_Id(int park_Id) {
		this.park_Id = park_Id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOpenFromMonth() {
		return openFromMonth;
	}
	public void setOpenFromMonth(int openFromMonth) {
		this.openFromMonth = openFromMonth;
	}
	public int getOpenToMonth() {
		return openToMonth;
	}
	public void setOpenToMonth(int openToMonth) {
		this.openToMonth = openToMonth;
	}
	public int getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(int dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	
	

}
