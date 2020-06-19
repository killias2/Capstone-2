package com.techelevator.model;

import java.time.LocalDate;

public class Reservation extends Site{
	
	private long reservationId;
	private String reservationName;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate createDate;
	
	public long getReservationId() {
		return reservationId;
	}
	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}
	public String getReservationName() {
		return reservationName;
	}
	public void setReservationName(String name) {
		this.reservationName = name;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	
	

}
