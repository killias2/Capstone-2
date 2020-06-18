package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

public interface ParkDAO {
	
	public List<Park> returnAllParks();
	public List<Campground> returnAllCampgrounds(int parkId);
	public List<Site> returnAllAvailableSites(ReservationSearch search);
	public List<Site> returnAllAvailableSitesAdvanced(AdvancedSearch search);
	public List<Reservation> returnAllReservationsNext30Days(LocalDate currentDate, int parkId);
	
	

}
