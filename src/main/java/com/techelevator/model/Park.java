package com.techelevator.model;

import java.time.LocalDate;

public class Park {
	
	private int parkId;
	private String name;
	private String location;
	private LocalDate establishDate;
	private long area;
	private long visitors;
	private String description;
	
	public int getParkId() {
		return parkId;
	}
	public void setPark_id(int parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	

}
