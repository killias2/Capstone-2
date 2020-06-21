package com.techelevator;

import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.Campground;
import com.techelevator.model.JDBCReservationDAO;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;


public class JDBCReservationDAOIntegrationTest extends DAOIntegrationTest {
	
	JDBCReservationDAO dao;
	
	@Before
	public void setup() {
		dao = new JDBCReservationDAO(getDataSource());
	}
	
	@Test
	public void create_reservation_return_results() {
		
		Park newPark = createTestPark();
		
		Campground newCampground = createTestCampground(newPark.getParkId());
		
		Site newSite = createTestSite(newCampground.getCampgroundId(), newCampground.getCampName(), newCampground.getDailyFee());
		
		Reservation newReservation = createTestRes(newSite.getSiteId());
		
		Reservation actualReservation = dao.getReservationById(newReservation.getReservationId());

		assertReservationsAreEqual(newReservation, actualReservation);
	}
	
	

}
