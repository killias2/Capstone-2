package com.techelevator.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	@Override
	public List<Park> returnAllParks() {
		
		List<Park> parkList = new ArrayList<Park>();
		String sqlReturnAllParks = "SELECT park_id, name, location, establish_date, area, visitors, " +
				"description FROM park ORDER BY name";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllParks);
		while (results.next()) {
			Park thisPark = new Park();
			thisPark.setParkId(results.getInt("park_id"));
			thisPark.setParkName(results.getString("name"));
			thisPark.setLocation(results.getString("location"));
			thisPark.setEstablishDate(results.getDate("establish_date").toLocalDate());
			thisPark.setArea(results.getLong("area"));
			thisPark.setVisitors(results.getLong("visitors"));
			thisPark.setDescription(results.getString("description"));
			parkList.add(thisPark);
		}
		
		return parkList;
	}

	@Override
	public List<Campground> returnAllCampgrounds(int parkId) {
		
		List<Campground> campList = new ArrayList<Campground>();
		String sqlReturnAllParks = "SELECT campground_id, name, open_from_mm, open_to_mm, daily_fee::numeric) " +
				"FROM campground WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllParks, parkId);
		while (results.next()) {
			Campground thisCampground = new Campground();
			thisCampground.setParkId(parkId);
			thisCampground.setCampgroundId(results.getInt("campground_id"));
			thisCampground.setCampName(results.getString("name"));
			thisCampground.setOpenFromMonth(results.getString("open_from_mm"));
			thisCampground.setOpenToMonth(results.getString("open_to_mm"));
			thisCampground.setDailyFee(results.getInt("daily_fee"));
			campList.add(thisCampground);
		}
		
		return campList;
	}

	@Override
	public List<Site> returnAllAvailableSites(ReservationSearch search) {
		
//		List<Site> siteList = new ArrayList<Site>;
//		String sqlReturnAllAvailableSites = ""
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> returnAllAvailableSitesAdvanced(AdvancedSearch search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> returnAllReservationsNext30Days(LocalDate currentDate, int parkId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Park addPark(Park newPark) {
		String sqlInsertPark = "INSERT INTO park (park_id, name, location, establish_date, area, "
				+ "visitors, description) VALUES(?, ?, ?, ?, ?, ?, ?)";
		newPark.setParkId(getNextParkId());
		
		jdbcTemplate.update(sqlInsertPark, newPark.getParkId(), newPark.getParkName(), newPark.getLocation(), 
				newPark.getEstablishDate(), newPark.getArea(), newPark.getVisitors(), newPark.getDescription());
		
		return newPark;
	}
	
	private int getNextParkId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('park_park_id_seq')");
		if(nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException ("Something went wrong while getting an id for the new park"); 
		}
	}

}