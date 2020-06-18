package com.techelevator.model;

import java.time.LocalDate;

public class AdvancedSearch extends CampgroundSearch {
	
	int maxOccupancy;
	boolean accessible;
	int maxRVLength;
	boolean utilities;
	
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
	public int getMaxRVLength() {
		return maxRVLength;
	}
	public void setMaxRVLength(int maxRVLength) {
		this.maxRVLength = maxRVLength;
	}
	public boolean isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	public AdvancedSearch(LocalDate searchFromDate, LocalDate searchToDate, int searchCampgroundId,
			int searchMaxOccupancy, boolean isAccessible, int searchMaxRVLength, boolean isUtilities) {
		super (searchFromDate, searchToDate, searchCampgroundId);
		maxOccupancy = searchMaxOccupancy;
		accessible = isAccessible;
		maxRVLength = searchMaxRVLength;
		utilities = isUtilities;
}
	
	

}
