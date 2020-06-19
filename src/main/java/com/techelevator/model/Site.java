package com.techelevator.model;

public class Site extends Campground{
	
	private long siteId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean accessible;
	private int maxRVlength;
	private boolean utilities;
	
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	public int getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
		return accessible;
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public int getMaxRVlength() {
		return maxRVlength;
	}
	public void setMaxRVlength(int maxRVlength) {
		this.maxRVlength = maxRVlength;
	}
	public boolean isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	

}
