package com.techelevator.model;

import java.time.LocalDate;

public class Park {
	
	private int parkId;
	private String parkName;
	private String location;
	private LocalDate establishDate;
	private long area;
	private long visitors;
	private String description;
	private int parkRow;
	
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String name) {
		this.parkName = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstablishDate() {
		return establishDate;
	}
	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}
	public long getArea() {
		return area;
	}
	public void setArea(long area) {
		this.area = area;
	}
	public long getVisitors() {
		return visitors;
	}
	public void setVisitors(long visitors) {
		this.visitors = visitors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getParkRow() {
		return parkRow;
	}
	public void setParkRow(int parkRow) {
		this.parkRow = parkRow;
	}
	
	
	

}
