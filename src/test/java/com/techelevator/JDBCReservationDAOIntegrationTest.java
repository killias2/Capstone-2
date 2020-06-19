package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.Campground;
import com.techelevator.model.JDBCCampgroundDAO;
import com.techelevator.model.JDBCParkDAO;
import com.techelevator.model.JDBCReservationDAO;
import com.techelevator.model.JDBCSiteDAO;
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
	public void create_reservation() {
		
		JDBCParkDAO parkDao = new JDBCParkDAO(getDataSource());
		Park newPark = new Park();
		newPark.setParkName("ZZZZZYYYY PARK");
		newPark.setLocation("Stevehio");
		newPark.setEstablishDate(LocalDate.of(2020, 06, 17));
		newPark.setArea(10000);
		newPark.setVisitors(100);
		newPark.setDescription("This is a test park, which is not very popular and which also doesn't exist");
		parkDao.addPark(newPark);
		
		JDBCCampgroundDAO campgroundDAO = new JDBCCampgroundDAO(getDataSource());
		Campground newCampground = new Campground();
		newCampground.setParkId(newPark.getParkId());
		newCampground.setCampName("ZZZZYYYYY Bobby Tables Campground");
		newCampground.setOpenFromMonth("01");
		newCampground.setOpenToMonth("11");
		newCampground.setDailyFee(100);
		campgroundDAO.addCampground(newCampground);
		
		JDBCSiteDAO siteDao = new JDBCSiteDAO(getDataSource());
		Site newSite = new Site();
		newSite.setCampgroundId(newCampground.getCampgroundId());
		newSite.setSiteNumber(1);
		newSite.setMaxOccupancy(10);
		newSite.setAccessible(true);
		newSite.setMaxRVlength(0);
		newSite.setUtilities(false);
		siteDao.addSite(newSite);
		
		Reservation newReservation = new Reservation();
		newReservation.setReservationName("ZZZYYY Family");
		newReservation.setSiteId(newSite.getSiteId());
		LocalDate fromDate = LocalDate.of(2020, 8, 20);
		LocalDate toDate = LocalDate.of(2020, 8, 27);
		LocalDate createDate = LocalDate.now();
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
		assertEquals(expected.getReservationName(), actual.getReservationName());
		assertEquals(expected.getFromDate(), actual.getFromDate());
		assertEquals(expected.getToDate(), actual.getToDate());
		assertEquals(expected.getCreateDate(), actual.getCreateDate());
	}
	
	

}
