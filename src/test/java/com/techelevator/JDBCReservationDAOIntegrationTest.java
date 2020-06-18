package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.JDBCReservationDAO;
import com.techelevator.model.Reservation;


public class JDBCReservationDAOIntegrationTest extends DAOIntegrationTest {
	
	JDBCReservationDAO dao;
	
	@Before
	public void setup() {
		dao = new JDBCReservationDAO(getDataSource());
	}
	
	@Test
	public void create_reservation() {
		
		//add park
		
		//add campground
		
		//add site
		
		Reservation newReservation = new Reservation();
		newReservation.setName("ZZZYYY Family");
		newReservation.setSiteId(46); //add from new site, later
		LocalDate fromDate = LocalDate.of(2020, 8, 20);
		LocalDate toDate = LocalDate.of(2020, 8, 27);
		LocalDate createDate = LocalDate.of(2020, 06, 17);
		newReservation.setFromDate(fromDate);
		newReservation.setToDate(toDate);
		newReservation.setCreateDate(createDate);
		dao.addReservation(newReservation);
		
		Reservation actualReservation = dao.getReservationById(newReservation.getReservationId());

		assertReservationsAreEqual(newReservation, actualReservation);
	}
	
	private void assertReservationsAreEqual(Reservation expected, Reservation actual) {
		assertEquals(expected.getReservationId(), actual.getReservationId());
		assertEquals(expected.getSiteId(), actual.getSiteId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getFromDate(), actual.getFromDate());
		assertEquals(expected.getToDate(), actual.getToDate());
		assertEquals(expected.getCreateDate(), actual.getCreateDate());
	}
	
	

}
