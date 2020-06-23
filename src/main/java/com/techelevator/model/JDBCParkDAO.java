package com.techelevator.model;

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
				"description, ROW_NUMBER() OVER() FROM park ORDER BY name";
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
			thisPark.setParkRow(results.getInt("row_number"));
			parkList.add(thisPark);
		}
		
		return parkList;
	}

	@Override
	public List<Campground> returnAllCampgrounds(int parkId) {
		
		List<Campground> campList = new ArrayList<Campground>();
		String sqlReturnAllParks = "SELECT campground_id, name, open_from_mm, open_to_mm, daily_fee::numeric::integer " +
				"FROM campground WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllParks, parkId);
		while (results.next()) {
			Campground thisCampground = new Campground();
			thisCampground.setParkId(parkId);
			thisCampground.setCampgroundId(results.getInt("campground_id"));
			thisCampground.setCampName(results.getString("name"));
			thisCampground.setOpenFromMonth(results.getString("open_from_mm"));
			thisCampground.setOpenToMonth(results.getString("open_to_mm"));
			thisCampground.setDailyFee((int)results.getDouble(("daily_fee")));
			campList.add(thisCampground);
		}
		
		return campList;
	}

	@Override
	public List<Site> returnAllAvailableSites(ReservationSearch search) {
		List<Site> siteList = new ArrayList<Site>();
		String sqlReturnAllAvailableSites = "SELECT row_filter.* FROM (SELECT campground.campground_id, "
				+ "campground.name, site.site_id, site_number, max_occupancy, accessible, max_rv_length, utilities, campground.daily_fee, "
				+ "count(r.reservation_id) pop, ROW_NUMBER() OVER (PARTITION BY campground.campground_id "
				+ "ORDER BY count(r.reservation_id) DESC, site.site_id) FROM site " 
				+ "LEFT JOIN reservation r ON site.site_id = r.site_id " 
				+ "LEFT JOIN campground ON site.campground_id = campground.campground_id WHERE "
				+ "campground.park_id = ? AND site.site_id NOT IN ( "
				+ "      SELECT r.site_id "
				+ "      FROM reservation r "
				+ "      WHERE \n"
				+ "      (? BETWEEN r.from_date AND r.to_date) "
				+ "      OR (? BETWEEN r.from_date AND r.to_date) " 
				+ "      OR (? < r.from_date AND ? > r.from_date) " 
				+ "        ) " 
				+ "AND        " 
				+ "site.site_id IN ( " 
				+ "      SELECT site.site_id " 
				+ "      FROM site " 
				+ "      WHERE " 
				+ "      (DATE_PART ('month', ?) "
				+ "      >= (campground.open_from_mm::double precision) " 
				+ "      AND "
				+ "      DATE_PART ('month', ?) <= "
				+ "      (campground.open_to_mm::double precision)) " 
				+ "      AND " 
				+ "      (DATE_PART ('month', ?) >= "
				+ "      (campground.open_from_mm::double precision) " 
				+ "      AND " 
				+ "      DATE_PART ('month', ?) <= (campground.open_to_mm::double precision)) " 
				+ ") "
				+ "GROUP BY campground.campground_id, site.site_id " 
				+ "ORDER BY campground.campground_id, ROW_NUMBER " 
				+ "      ) " 
				+ "      row_filter WHERE ROW_NUMBER < 6"; 
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllAvailableSites, search.getParkId(), search.getFromDate(), 
				search.getToDate(), search.getFromDate(), search.getToDate(), search.getFromDate(), 
				search.getFromDate(), search.getToDate(), search.getToDate());
		
		while (results.next()) {
			Site newSite = new Site();
			newSite = mapRowToSite(results);
			siteList.add(newSite);
		}
		
		return siteList;
	}

	@Override
	public List<Site> returnAllAvailableSitesAdvanced(AdvancedSearch search) {
		List<Site> siteList = new ArrayList<Site>();
		String sqlReturnAllAvailableSites = "SELECT row_filter.* FROM (SELECT campground.campground_id, "
				+ "campground.name, site.site_id, site_number, max_occupancy, accessible, max_rv_length, utilities, campground.daily_fee, "
				+ "count(r.reservation_id) pop, ROW_NUMBER() OVER (PARTITION BY campground.campground_id "
				+ "ORDER BY count(r.reservation_id) DESC, site.site_id) FROM site " 
				+ "LEFT JOIN reservation r ON site.site_id = r.site_id " 
				+ "LEFT JOIN campground ON site.campground_id = campground.campground_id WHERE "
				+ "campground.park_id = ? AND site.site_id NOT IN ( "
				+ "      SELECT r.site_id "
				+ "      FROM reservation r "
				+ "      WHERE "
				+ "      (? BETWEEN r.from_date AND r.to_date) "
				+ "      OR (? BETWEEN r.from_date AND r.to_date) " 
				+ "      OR (? < r.from_date AND ? > r.from_date) " 
				+ "        ) " 
				+ "AND " 
				+ "site.site_id IN ( " 
				+ "      SELECT site.site_id " 
				+ "      FROM site " 
				+ "      WHERE " 
				+ "      (DATE_PART ('month', ?) "
				+ "      >= (campground.open_from_mm::double precision) " 
				+ "      AND "
				+ "      DATE_PART ('month', ?) <= "
				+ "      (campground.open_to_mm::double precision)) " 
				+ "      AND " 
				+ "      (DATE_PART ('month', ?) >= "
				+ "      (campground.open_from_mm::double precision) " 
				+ "      AND " 
				+ "      DATE_PART ('month', ?) <= (campground.open_to_mm::double precision)) " 
				+ ") "
				+ "AND "
				+ "site.site_id IN ( "
				+ "     SELECT site.site_id "
				+ "		FROM site "
				+ "		WHERE "
				+ "		max_occupancy >= ? "
				+ "     AND accessible >= ? "
				+ "		AND max_rv_length >= ? "
				+ " 	AND utilities >= ?"
				+ "     ) "
				+ "GROUP BY campground.campground_id, site.site_id " 
				+ "ORDER BY campground.campground_id, ROW_NUMBER " 
				+ "      ) " 
				+ "      row_filter WHERE ROW_NUMBER < 6"; 
		
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnAllAvailableSites, search.getParkId(), search.getFromDate(), 
				search.getToDate(), search.getFromDate(), search.getToDate(), search.getFromDate(), 
				search.getFromDate(), search.getToDate(), search.getToDate(), search.getMaxOccupancy(), search.isAccessible(),
				search.getMaxRVLength(), search.isUtilities());
		
		while (results.next()) {
			Site newSite = new Site();
			newSite = mapRowToSite(results);
			siteList.add(newSite);
		}
		
		return siteList;
	}

	@Override
	public List<Reservation> returnAllReservationsNext30Days(int parkId) {
		List<Reservation> resList = new ArrayList<>();
		String sqlReturn30DaysReservations = "SELECT campground.name as campname, r.name as resname, reservation_id, s.site_number, from_date, " +
				"to_date, create_date\n" + 
				"FROM reservation r\n" + 
				"JOIN site s ON r.site_id = s.site_id\n" + 
				"JOIN campground ON s.campground_id = campground.campground_id\n" + 
				"WHERE\n" + 
				"campground.park_id = ?\n" + 
				"AND\n" + 
				"(\n" + 
				"from_date BETWEEN (CURRENT_DATE) AND (CURRENT_DATE + INTERVAL '30 day')\n" + 
				"OR\n" + 
				"to_date BETWEEN (CURRENT_DATE) AND (CURRENT_DATE + INTERVAL '30 day')\n" + 
				"OR\n" + 
				"CURRENT_DATE BETWEEN from_date AND to_date) " +
				"ORDER BY from_date, to_date, campname, site_number";
		
				SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturn30DaysReservations, parkId);
				
		while (results.next()) {
			Reservation newRes = new Reservation();
			newRes = mapRowToReservation(results);
			resList.add(newRes);
		}
		return resList;
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
	
	private Site mapRowToSite(SqlRowSet results) {
		Site newSite = new Site();
		newSite.setCampName(results.getString("name"));
		newSite.setSiteId(results.getLong("site_id"));
		newSite.setCampgroundId(results.getInt("campground_id"));
		newSite.setSiteNumber(results.getInt("site_number"));
		newSite.setMaxOccupancy(results.getInt("max_occupancy"));
		newSite.setAccessible(results.getBoolean("accessible"));
		newSite.setMaxRVlength(results.getInt("max_rv_length"));
		newSite.setUtilities(results.getBoolean("utilities"));
		newSite.setDailyFee((int)results.getDouble(("daily_fee")));
		return newSite;
	}
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation someReservation = new Reservation();
		someReservation.setReservationId(results.getLong("reservation_id"));
		someReservation.setCampName(results.getString("campname"));
		someReservation.setSiteNumber(results.getInt("site_number"));
		someReservation.setReservationName(results.getString("resname"));
		someReservation.setFromDate(results.getDate("from_date").toLocalDate());
		someReservation.setToDate(results.getDate("to_date").toLocalDate());
		someReservation.setCreateDate(results.getDate("create_date").toLocalDate());
		return someReservation;
	}

}