package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

public interface ParkDAO {
	
	public List<Park> returnAllParks();
		//^ returns a list of all rows on the list table represented by Park class objects.  
		//  Creates an instance of Park for each row of park db table, and stores them all in a List.
	public List<Campground> returnAllCampgrounds(int parkId);
		//^ returns a List
		//  Creates an instance of Campground for each row of campground table, and stores them all in a List
	public List<Site> returnAllAvailableSites(ReservationSearch search);
	public List<Site> returnAllAvailableSitesAdvanced(AdvancedSearch search);
	public List<Reservation> returnAllReservationsNext30Days(LocalDate currentDate, int parkId);
	
	

}
