package com.techelevator.model;

import java.time.LocalDate;

public class CampgroundSearch extends ReservationSearch{
	
	private int campgroundId;

	public int getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(int campgroundId) {
		this.campgroundId = campgroundId;
	}
	
	public CampgroundSearch(LocalDate searchFromDate, LocalDate searchToDate, int searchCampgroundId) {
		super(searchFromDate, searchToDate);
		campgroundId = searchCampgroundId;
	}

}
