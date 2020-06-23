package com.techelevator;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.AdvancedSearch;
import com.techelevator.model.Campground;
import com.techelevator.model.JDBCParkDAO;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationSearch;
import com.techelevator.model.Site;


public class JDBCParkDAOIntegrationTest extends DAOIntegrationTest{
	
	JDBCParkDAO dao;

	@Before
	public void setup() {
		dao = new JDBCParkDAO(getDataSource());
	}
	
	@Test
	public void return_all_parks() {
		Park newPark = createTestPark();
		List<Park> parkList = dao.returnAllParks();
		
		Park expectedPark = new Park();
		expectedPark.setParkId(newPark.getParkId());
		expectedPark.setParkName("ZZZZZYYYY PARK");
		expectedPark.setLocation("Stevehio");
		expectedPark.setEstablishDate(LocalDate.of(2020, 06, 17));
		expectedPark.setArea(10000);
		expectedPark.setVisitors(100);
		expectedPark.setDescription("This is a test park, which is not very popular and which also doesn't exist");
		expectedPark.setParkRow(4);
		
		Assert.assertTrue(parkList.size() >= 1);
		assertParksAreEqual(parkList.get(3), expectedPark);
	}
	
	@Test
	public void return_campgrounds_in_park() {
		Park newPark = createTestPark();
		Campground newCampground = createTestCampground(newPark.getParkId());
		
		List<Campground> campgroundList = dao.returnAllCampgrounds(newPark.getParkId());
		
		Campground expectedCampground = new Campground();
		expectedCampground.setCampgroundId(newCampground.getCampgroundId());
		expectedCampground.setParkId(newCampground.getParkId());
		expectedCampground.setCampName(newCampground.getCampName());
		expectedCampground.setOpenFromMonth(newCampground.getOpenFromMonth());
		expectedCampground.setOpenToMonth(newCampground.getOpenToMonth());
		expectedCampground.setDailyFee(newCampground.getDailyFee());
		
		Assert.assertTrue(campgroundList.size() == 1);
		assertCampgroundsAreEqual(campgroundList.get(0), expectedCampground);
	}
	
	@Test
	public void return_top_sites_park_wide() { //not dependent on current state of database
		
		Park newPark = createTestPark();
		
		Campground newCampground = createTestCampground(newPark.getParkId());
		
		Site newSite = createTestSite(newCampground.getCampgroundId(), newCampground.getCampName(), newCampground.getDailyFee());
		Site newSite2 = createTestSite2(newCampground.getCampgroundId(), newCampground.getCampName(), newCampground.getDailyFee());
	
		
		createTestRes(newSite2.getSiteId());
		createTestRes2(newSite2.getSiteId());
		
		
		List<Site> siteList = new ArrayList<>();
		LocalDate fromDate = LocalDate.of(2020, 7, 17);
		LocalDate toDate = LocalDate.of(2020,  07, 23);
		ReservationSearch search = new ReservationSearch(fromDate, toDate);
		search.setParkId(newPark.getParkId());
		siteList = dao.returnAllAvailableSites(search);
		
		Site expectedSite1 = new Site();
		expectedSite1.setCampgroundId(newSite.getCampgroundId());
		expectedSite1.setSiteId(newSite.getSiteId());
		expectedSite1.setSiteNumber(newSite.getSiteNumber());
		expectedSite1.setMaxOccupancy(newSite.getMaxOccupancy());
		expectedSite1.setAccessible(newSite.isAccessible());
		expectedSite1.setMaxRVlength(newSite.getMaxRVlength());
		expectedSite1.setUtilities(newSite.isUtilities());
		
		Site expectedSite2 = new Site();
		expectedSite2.setCampgroundId(newSite2.getCampgroundId());
		expectedSite2.setSiteId(newSite2.getSiteId());
		expectedSite2.setSiteNumber(newSite2.getSiteNumber());
		expectedSite2.setMaxOccupancy(newSite2.getMaxOccupancy());
		expectedSite2.setAccessible(newSite2.isAccessible());
		expectedSite2.setMaxRVlength(newSite2.getMaxRVlength());
		expectedSite2.setUtilities(newSite2.isUtilities());
		
		
		Assert.assertTrue(siteList.size() >= 1);
		assertSitesAreEqual(expectedSite2, siteList.get(0));
		assertSitesAreEqual(expectedSite1, siteList.get(1));
	}
	
	
	@Test
	public void return_top_sites_park_wide_currentDB() { //more robust, but current DB dependent
		List<Site> siteList = new ArrayList<>();
		LocalDate fromDate = LocalDate.of(2020, 07, 17);
		LocalDate toDate = LocalDate.of(2020, 07, 23);
		ReservationSearch search = new ReservationSearch(fromDate, toDate);
		search.setParkId(1);
		siteList = dao.returnAllAvailableSites(search);
		
		Site expectedSite1 = new Site();
		expectedSite1.setCampgroundId(1);
		expectedSite1.setSiteId(46);
		expectedSite1.setSiteNumber(46);
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
		
		Site expectedSite3 = new Site();
		expectedSite3.setCampgroundId(1);
		expectedSite3.setSiteId(25);
		expectedSite3.setSiteNumber(25);
		expectedSite3.setMaxOccupancy(6);
		expectedSite3.setAccessible(false);
		expectedSite3.setMaxRVlength(0);
		expectedSite3.setUtilities(false);
			
			
		Assert.assertTrue(siteList.size() >= 1);
		assertSitesAreEqual(expectedSite1, siteList.get(0));
		assertSitesAreEqual(expectedSite2, siteList.get(5));
		assertSitesAreEqual(expectedSite3, siteList.get(4));
	}
	
	
	@Test
	public void return_top_sites_park_wide_advanced() { //less robust, but not current db dependent
		
		Park newPark = createTestPark();
		
		Campground newCampground = createTestCampground(newPark.getParkId());
		
		Site newSite = createTestSite(newCampground.getCampgroundId(), newCampground.getCampName(), newCampground.getDailyFee());
		Site newSite2 = createTestSite2(newCampground.getCampgroundId(), newCampground.getCampName(), newCampground.getDailyFee());
	
		createTestRes(newSite2.getSiteId());
		createTestRes2(newSite2.getSiteId());
		
		
		List<Site> siteList = new ArrayList<>();
		LocalDate fromDate = LocalDate.of(2020, 07, 17);
		LocalDate toDate = LocalDate.of(2020, 07, 23);
		int maxOccupancy = 10;
		boolean accessible = true;
		int maxRVlength = 0;
		boolean utilities = false;
		AdvancedSearch search = new AdvancedSearch(fromDate, toDate, maxOccupancy, accessible, maxRVlength, utilities);
		search.setParkId(newPark.getParkId());
		siteList = dao.returnAllAvailableSitesAdvanced(search);
		
		Site expectedSite1 = new Site();
		expectedSite1.setCampgroundId(newCampground.getCampgroundId());
		expectedSite1.setSiteId(newSite.getSiteId());
		expectedSite1.setSiteNumber(newSite.getSiteNumber());
		expectedSite1.setMaxOccupancy(newSite.getMaxOccupancy());
		expectedSite1.setAccessible(newSite.isAccessible());
		expectedSite1.setMaxRVlength(newSite.getMaxRVlength());
		expectedSite1.setUtilities(newSite.isUtilities());
		
		
		Assert.assertTrue(siteList.size() == 1);
		assertSitesAreEqual(expectedSite1, siteList.get(0));
	}
	
	
	@Test
	public void return_top_sites_park_wide_advanced_currentDB() { //more robust, but current DB dependent
		List<Site> siteList = new ArrayList<>();
		LocalDate fromDate = LocalDate.of(2020, 07, 17);
		LocalDate toDate = LocalDate.of(2020, 07, 23);
		int maxOccupancy = 6;
		boolean accessible = false;
		int maxRVlength = 0;
		boolean utilities = true;
		AdvancedSearch search = new AdvancedSearch(fromDate, toDate, maxOccupancy, accessible, maxRVlength, utilities);
		search.setParkId(1);
		siteList = dao.returnAllAvailableSitesAdvanced(search);
		
		Site expectedSite1 = new Site();
		expectedSite1.setCampName("Blackwoods");
		expectedSite1.setCampgroundId(1);
		expectedSite1.setSiteId(178);
		expectedSite1.setSiteNumber(178);
		expectedSite1.setMaxOccupancy(6);
		expectedSite1.setAccessible(false);
		expectedSite1.setMaxRVlength(0);
		expectedSite1.setUtilities(true);
		
		Site expectedSite2 = new Site();
		expectedSite1.setCampName("Seawall");
		expectedSite2.setCampgroundId(2);
		expectedSite2.setSiteId(392);
		expectedSite2.setSiteNumber(116);
		expectedSite2.setMaxOccupancy(6);
		expectedSite2.setAccessible(false);
		expectedSite2.setMaxRVlength(0);
		expectedSite2.setUtilities(true);
		
		
		Assert.assertTrue(siteList.size() >= 1);
		assertSitesAreEqual(expectedSite1, siteList.get(0));
		assertSitesAreEqual(expectedSite2, siteList.get(5));
	}
	
	@Test
	public void return_all_res_30_Days() {
		Park newPark = createTestPark();
		
		Campground newCampground = createTestCampground(newPark.getParkId());
		
		Site newSite = createTestSite(newCampground.getCampgroundId(), newCampground.getCampName(), newCampground.getDailyFee());
		Site newSite2 = createTestSite2(newCampground.getCampgroundId(), newCampground.getCampName(), newCampground.getDailyFee());
	
		createTestRes1Return30(newSite.getSiteId(), newCampground.getCampName(), newSite.getSiteNumber());
		createTestRes2Return30(newSite2.getSiteId(), newCampground.getCampName(), newSite2.getSiteNumber());
		createTestRes3Return30(newSite2.getSiteId(), newCampground.getCampName(), newSite2.getSiteNumber());
		
		List<Reservation> resList = new ArrayList<>();
		resList = dao.returnAllReservationsNext30Days(newPark.getParkId());
		Assert.assertTrue(resList.size() ==3 );
		
		
	}
	
	

}