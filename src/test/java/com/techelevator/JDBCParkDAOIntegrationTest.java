package com.techelevator;


import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.JDBCParkDAO;
import com.techelevator.model.Park;
import com.techelevator.model.ReservationSearch;
import com.techelevator.model.Site;


public class JDBCParkDAOIntegrationTest extends DAOIntegrationTest{
	
	JDBCParkDAO dao;

	@Before
	public void setup() {
		dao = new JDBCParkDAO(getDataSource());
	}
	
	@Test
	public void returnTopSitesParkWide() {
		List<Site> siteList = new ArrayList<>();
		LocalDate fromDate = LocalDate.of(2020, 7, 17);
		LocalDate toDate = LocalDate.of(2020,  07, 23);
		ReservationSearch search = new ReservationSearch(fromDate, toDate);
		search.setParkId(1);
		siteList = dao.returnAllAvailableSites(search);
		
		Site expectedSite1 = new Site();
		expectedSite1.setCampgroundId(1);
		expectedSite1.setSiteId(1);
		expectedSite1.setSiteNumber(1);
		expectedSite1.setMaxOccupancy(6);
		expectedSite1.setAccessible(false);
		expectedSite1.setMaxRVlength(0);
		expectedSite1.setUtilities(false);
		
		Site expectedSite2 = new Site();
		expectedSite2.setCampgroundId(2);
		expectedSite2.setSiteId(277);
		expectedSite2.setSiteNumber(1);
		expectedSite2.setMaxOccupancy(6);
		expectedSite2.setAccessible(false);
		expectedSite2.setMaxRVlength(0);
		expectedSite2.setUtilities(false);
		
		
		Assert.assertTrue(siteList.size() >= 1);
		assertSitesAreEqual(expectedSite1, siteList.get(0));
		assertSitesAreEqual(expectedSite2, siteList.get(5));
	}
	
	private void assertSitesAreEqual(Site expected, Site actual) {
	assertEquals(expected.getSiteId(), actual.getSiteId());
	assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
	assertEquals(expected.getSiteNumber(), actual.getSiteNumber());
	assertEquals(expected.getMaxOccupancy(), actual.getMaxOccupancy());
	assertEquals(expected.isAccessible(), actual.isAccessible());
	assertEquals(expected.getMaxRVlength(), actual.getMaxRVlength());
	assertEquals(expected.isUtilities(), actual.isUtilities());
}
	
	
	

}

//@Test
//public void create_reservation() {
//	
//	JDBCParkDAO parkDao = new JDBCParkDAO(getDataSource());
//	Park newPark = new Park();
//	newPark.setParkName("ZZZZZYYYY PARK");
//	newPark.setLocation("Stevehio");
//	newPark.setEstablishDate(LocalDate.of(2020, 06, 17));
//	newPark.setArea(10000);
//	newPark.setVisitors(100);
//	newPark.setDescription("This is a test park, which is not very popular and which also doesn't exist");
//	parkDao.addPark(newPark);
//	
//	JDBCCampgroundDAO campgroundDAO = new JDBCCampgroundDAO(getDataSource());
//	Campground newCampground = new Campground();
//	newCampground.setParkId(newPark.getParkId());
//	newCampground.setCampName("ZZZZYYYYY Bobby Tables Campground");
//	newCampground.setOpenFromMonth("01");
//	newCampground.setOpenToMonth("11");
//	newCampground.setDailyFee(100);
//	campgroundDAO.addCampground(newCampground);
//	
//	JDBCSiteDAO siteDao = new JDBCSiteDAO(getDataSource());
//	Site newSite = new Site();
//	newSite.setCampgroundId(newCampground.getCampgroundId());
//	newSite.setSiteNumber(1);
//	newSite.setMaxOccupancy(10);
//	newSite.setAccessible(true);
//	newSite.setMaxRVlength(0);
//	newSite.setUtilities(false);
//	siteDao.addSite(newSite);
//	
//	Reservation newReservation = new Reservation();
//	newReservation.setReservationName("ZZZYYY Family");
//	newReservation.setSiteId(newSite.getSiteId());
//	LocalDate fromDate = LocalDate.of(2020, 8, 20);
//	LocalDate toDate = LocalDate.of(2020, 8, 27);
//	LocalDate createDate = LocalDate.now();
//	newReservation.setFromDate(fromDate);
//	newReservation.setToDate(toDate);
//	newReservation.setCreateDate(createDate);
//	dao.addReservation(newReservation);
//	
//	Reservation actualReservation = dao.getReservationById(newReservation.getReservationId());
//
//	assertReservationsAreEqual(newReservation, actualReservation);
//}
//
//private void assertReservationsAreEqual(Reservation expected, Reservation actual) {
//	assertEquals(expected.getReservationId(), actual.getReservationId());
//	assertEquals(expected.getSiteId(), actual.getSiteId());
//	assertEquals(expected.getReservationName(), actual.getReservationName());
//	assertEquals(expected.getFromDate(), actual.getFromDate());
//	assertEquals(expected.getToDate(), actual.getToDate());
//	assertEquals(expected.getCreateDate(), actual.getCreateDate());
//}