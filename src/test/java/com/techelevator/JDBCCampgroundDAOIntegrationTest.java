package com.techelevator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.AdvancedSearch;
import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundSearch;
import com.techelevator.model.JDBCCampgroundDAO;
import com.techelevator.model.Park;
import com.techelevator.model.Site;

public class JDBCCampgroundDAOIntegrationTest extends DAOIntegrationTest {
	
	JDBCCampgroundDAO dao;
	
	@Before
	public void setup() {
		dao = new JDBCCampgroundDAO(getDataSource());
	}
	
	@Test
	public void  return_top_available_sites_in_campground() { //not current DB dependent
		Park newPark = createTestPark();
		
		Campground newCampground = createTestCampground(newPark.getParkId());
		
		Site newSite = createTestSite(newCampground.getCampgroundId());
		Site newSite2 = createTestSite2(newCampground.getCampgroundId());
		
		createTestRes(newSite2.getSiteId());
		createTestRes2(newSite2.getSiteId());
		
		
		List<Site> siteList = new ArrayList<>();
		LocalDate fromDate = LocalDate.of(2020, 7, 17);
		LocalDate toDate = LocalDate.of(2020,  07, 23);
		CampgroundSearch search = new CampgroundSearch(fromDate, toDate);
		search.setCampgroundId(newCampground.getCampgroundId());
		siteList = dao.returnTopAvailableSites(search);
		
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
	public void return_top_available_sites_in_campground_currentDB() { //more robust, but current DB dependent
		List<Site> siteList = new ArrayList<>();
		LocalDate fromDate = LocalDate.of(2020, 7, 17);
		LocalDate toDate = LocalDate.of(2020,  07, 23);
		CampgroundSearch search = new CampgroundSearch(fromDate, toDate);
		search.setCampgroundId(1);
		siteList = dao.returnTopAvailableSites(search);
		
//		Site expectedSite1 = new Site();   alter based on actual results
//		expectedSite1.setCampgroundId(1);
//		expectedSite1.setSiteId(1);
//		expectedSite1.setSiteNumber(1);
//		expectedSite1.setMaxOccupancy(6);
//		expectedSite1.setAccessible(false);
//		expectedSite1.setMaxRVlength(0);
//		expectedSite1.setUtilities(false);
//			
//		Site expectedSite2 = new Site();
//		expectedSite2.setCampgroundId(2);
//		expectedSite2.setSiteId(277);
//		expectedSite2.setSiteNumber(1);
//		expectedSite2.setMaxOccupancy(6);
//		expectedSite2.setAccessible(false);
//		expectedSite2.setMaxRVlength(0);
//		expectedSite2.setUtilities(false);
			
			
		Assert.assertTrue(siteList.size() >= 1);
//		assertSitesAreEqual(expectedSite1, siteList.get(0));
//		assertSitesAreEqual(expectedSite2, siteList.get(5));
	}
	
	@Test
	public void  return_top_available_sites_in_campground_advanced() { //not current DB dependent
		Park newPark = createTestPark();
		
		Campground newCampground = createTestCampground(newPark.getParkId());
		
		Site newSite = createTestSite(newCampground.getCampgroundId());
		Site newSite2 = createTestSite2(newCampground.getCampgroundId());
		
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
		search.setCampgroundId(newCampground.getCampgroundId());
		siteList = dao.returnTopSitesRequirements(search);
		
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
	public void return_top_available_sites_in_campground_advanced_currentDB() { //more robust, but current DB dependent
		List<Site> siteList = new ArrayList<>();
		LocalDate fromDate = LocalDate.of(2020, 07, 17);
		LocalDate toDate = LocalDate.of(2020, 07, 23);
		int maxOccupancy = 6;
		boolean accessible = false;
		int maxRVlength = 0;
		boolean utilities = true;
		AdvancedSearch search = new AdvancedSearch(fromDate, toDate, maxOccupancy, accessible, maxRVlength, utilities);
		search.setCampgroundId(1);
		siteList = dao.returnTopSitesRequirements(search);
		
//		Site expectedSite1 = new Site();   alter based on actual results
//		expectedSite1.setCampgroundId(1);
//		expectedSite1.setSiteId(178);
//		expectedSite1.setSiteNumber(178);
//		expectedSite1.setMaxOccupancy(6);
//		expectedSite1.setAccessible(false);
//		expectedSite1.setMaxRVlength(0);
//		expectedSite1.setUtilities(true);
//		
//		Site expectedSite2 = new Site();
//		expectedSite2.setCampgroundId(2);
//		expectedSite2.setSiteId(392);
//		expectedSite2.setSiteNumber(116);
//		expectedSite2.setMaxOccupancy(6);
//		expectedSite2.setAccessible(false);
//		expectedSite2.setMaxRVlength(0);
//		expectedSite2.setUtilities(true);
		
		
		Assert.assertTrue(siteList.size() >= 1);
//		assertSitesAreEqual(expectedSite1, siteList.get(0));
//		assertSitesAreEqual(expectedSite2, siteList.get(5));
	}

}