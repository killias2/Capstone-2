package com.techelevator;


import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.model.AdvancedSearch;
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
	
	@Test
	public void returnTopSitesParkWideAdvanced() {
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
		expectedSite1.setCampgroundId(1);
		expectedSite1.setSiteId(178);
		expectedSite1.setSiteNumber(178);
		expectedSite1.setMaxOccupancy(6);
		expectedSite1.setAccessible(false);
		expectedSite1.setMaxRVlength(0);
		expectedSite1.setUtilities(true);
		
		Site expectedSite2 = new Site();
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