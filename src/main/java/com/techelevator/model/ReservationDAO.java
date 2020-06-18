package com.techelevator.model;

import java.time.LocalDate;

public interface ReservationDAO {
	
	public Reservation addReservation(long siteid, String name, LocalDate fromDate,
			LocalDate toDate, LocalDate creationDate);
	
}
