package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.Campground;
import com.techelevator.model.JDBCCampgroundDAO;
import com.techelevator.model.JDBCParkDAO;
import com.techelevator.model.JDBCReservationDAO;
import com.techelevator.model.JDBCSiteDAO;
import com.techelevator.model.Park;
import com.techelevator.model.Reservation;
import com.techelevator.model.Site;

public abstract class DAOIntegrationTest {

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;

	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}

	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/* This method provides access to the DataSource for subclasses so that
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	protected void assertParksAreEqual(Park expected, Park actual) {
		assertEquals(expected.getParkId(), actual.getParkId());
		assertEquals(expected.getParkName(), actual.getParkName());
		assertEquals(expected.getLocation(), actual.getLocation());
		assertEquals(expected.getEstablishDate(), actual.getEstablishDate());
		assertEquals(expected.getArea(), actual.getArea());
		assertEquals(expected.getVisitors(), actual.getVisitors());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getParkRow(), actual.getParkRow());
	}
	
	protected void assertReservationsAreEqual(Reservation expected, Reservation actual) {
		assertEquals(expected.getReservationId(), actual.getReservationId());
		assertEquals(expected.getSiteId(), actual.getSiteId());
		assertEquals(expected.getReservationName(), actual.getReservationName());
		assertEquals(expected.getFromDate(), actual.getFromDate());
		assertEquals(expected.getToDate(), actual.getToDate());
		assertEquals(expected.getCreateDate(), actual.getCreateDate());
	}
	
	protected void assertReservationsAreEqualPW(Reservation expected, Reservation actual) {
		assertEquals(expected.getReservationId(), actual.getReservationId());
		assertEquals(expected.getSiteNumber(), actual.getSiteNumber());
		assertEquals(expected.getCampName(), actual.getCampName());
		assertEquals(expected.getFromDate(), actual.getFromDate());
		assertEquals(expected.getToDate(), actual.getToDate());
		assertEquals(expected.getCreateDate(), actual.getCreateDate());
	}
	
	protected void assertSitesAreEqual(Site expected, Site actual) {
		assertEquals(expected.getSiteId(), actual.getSiteId());
		assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
		assertEquals(expected.getSiteNumber(), actual.getSiteNumber());
		assertEquals(expected.getMaxOccupancy(), actual.getMaxOccupancy());
		assertEquals(expected.isAccessible(), actual.isAccessible());
		assertEquals(expected.getMaxRVlength(), actual.getMaxRVlength());
		assertEquals(expected.isUtilities(), actual.isUtilities());
	}
	
	protected void assertCampgroundsAreEqual(Campground expected, Campground actual) {
		assertEquals(expected.getCampgroundId(), actual.getCampgroundId());
		assertEquals(expected.getParkId(), actual.getParkId());
		assertEquals(expected.getCampName(), actual.getCampName());
		assertEquals(expected.getOpenFromMonth(), actual.getOpenFromMonth());
		assertEquals(expected.getOpenToMonth(), actual.getOpenToMonth());
		assertEquals(expected.getDailyFee(), actual.getDailyFee());
	}
	
	protected Park createTestPark() {
		JDBCParkDAO parkDao = new JDBCParkDAO(getDataSource());
		Park newPark = new Park();
		newPark.setParkName("ZZZZZYYYY PARK");
		newPark.setLocation("Stevehio");
		newPark.setEstablishDate(LocalDate.of(2020, 06, 17));
		newPark.setArea(10000);
		newPark.setVisitors(100);
		newPark.setDescription("This is a test park, which is not very popular and which also doesn't exist");
		parkDao.addPark(newPark);
		return newPark;
	}
	
	protected Campground createTestCampground(int parkId) {
		JDBCCampgroundDAO campgroundDAO = new JDBCCampgroundDAO(getDataSource());
		Campground newCampground = new Campground();
		newCampground.setParkId(parkId);
		newCampground.setCampName("ZZZZYYYYY Bobby Tables Campground");
		newCampground.setOpenFromMonth("01");
		newCampground.setOpenToMonth("11");
		newCampground.setDailyFee(100);
		campgroundDAO.addCampground(newCampground);
		return newCampground;
	}
	
	protected Site createTestSite(int campgroundId, String campName, int DailyFee) {
		JDBCSiteDAO siteDao = new JDBCSiteDAO(getDataSource());
		Site newSite = new Site();
		newSite.setCampgroundId(campgroundId);
		newSite.setCampName(campName);
		newSite.setSiteNumber(1);
		newSite.setMaxOccupancy(10);
		newSite.setAccessible(true);
		newSite.setMaxRVlength(0);
		newSite.setUtilities(false);
		newSite.setDailyFee(DailyFee);
		siteDao.addSite(newSite);
		return newSite;
	}
	
	protected Site createTestSite2(int campgroundId, String campName, int DailyFee) {
		JDBCSiteDAO siteDao = new JDBCSiteDAO(getDataSource());
		Site newSite = new Site();
		newSite.setCampgroundId(campgroundId);
		newSite.setCampName(campName);
		newSite.setSiteNumber(2);
		newSite.setMaxOccupancy(6);
		newSite.setAccessible(false);
		newSite.setMaxRVlength(10);
		newSite.setUtilities(true);
		newSite.setDailyFee(DailyFee);
		siteDao.addSite(newSite);
		return newSite;
	}
	
	protected Reservation createTestRes(long siteId) {
		JDBCReservationDAO dao = new JDBCReservationDAO(getDataSource());
		Reservation newReservation = new Reservation();
		newReservation.setReservationName("ZZZYYY Family");
		newReservation.setSiteId(siteId);
		LocalDate fromDate = LocalDate.of(2020, 8, 15);
		LocalDate toDate = LocalDate.of(2020, 8, 30);
		LocalDate createDate = LocalDate.now();
		newReservation.setFromDate(fromDate);
		newReservation.setToDate(toDate);
		newReservation.setCreateDate(createDate);
		dao.addReservation(newReservation);
		return newReservation;
	}
	
	protected Reservation createTestRes2(long siteId) {
		JDBCReservationDAO dao = new JDBCReservationDAO(getDataSource());
		Reservation newReservation = new Reservation();
		newReservation.setReservationName("ZXXYYY Family");
		newReservation.setSiteId(siteId);
		LocalDate fromDate = LocalDate.of(2020, 8, 01);
		LocalDate toDate = LocalDate.of(2020, 8, 14);
		LocalDate createDate = LocalDate.now();
		newReservation.setFromDate(fromDate);
		newReservation.setToDate(toDate);
		newReservation.setCreateDate(createDate);
		dao.addReservation(newReservation);
		return newReservation;
	}
	
}
