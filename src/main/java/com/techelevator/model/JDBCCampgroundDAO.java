package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	@Override //TODO finish this method
	public List<Site> returnTopAvailableSites(CampgroundSearch search) {
		List<Site> topAvailableSites = new ArrayList<>();
		String sqlReturnTopAvailableSites = "SELECT site.site_id, site_number, max_occupancy, accessible, max_rv_length, utilities, count(r.reservation_id) pop "
					+ "FROM site "
					+ "LEFT JOIN reservation r ON site.site_id = r.site_id "
					+ "LEFT JOIN campground ON site.campground_id = campground.campground_id "
					+ "WHERE campground.campground_id = ? "
					+ "AND "
					+ "site.site_id NOT IN ( "
					+ "SELECT r.site_id "
					+ "FROM reservation r "
					+ "WHERE " 
					+ "(? BETWEEN r.from_date AND r.to_date) OR (? BETWEEN r.from_date AND r.to_date) "
					+ "OR "
					+ "(? < r.from_date AND ? > r.from_date) "
					+ ") "
					+ "AND "
					+ "site.site_id IN ( "
					+ "SELECT site.site_id "
					+ "FROM site "
					+ "WHERE "
					+ "(DATE_PART ('month', ?) >= (campground.open_from_mm::double precision) "
					+ "AND "
					+ "DATE_PART ('month', ?) <= (campground.open_to_mm::double precision)) "
					+ "AND "
					+ "(DATE_PART ('month', ?) >= (campground.open_from_mm::double precision) "
					+ "AND "
					+ "DATE_PART ('month', ?) <= (campground.open_to_mm::double precision)) "
					+ ") "
					+ "GROUP BY site.site_id "
					+ "ORDER BY pop DESC, site.site_id "
					+ "LIMIT 5";  //TODO write SQL query
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnTopAvailableSites, search.getCampgroundId(), search.getFromDate(), search.getToDate(), search.getFromDate(), search.getToDate(), search.getFromDate(), search.getFromDate(), search.getToDate(), search.getToDate()); //fill out in order ? appear
		
		while(results.next()) {
			topAvailableSites.add(mapRowToSite(results));  //add each row as a new entry on the list
		}
		return topAvailableSites;
	}

	@Override //TODO finish this method
	public List<Site> returnTopSitesRequirements(AdvancedSearch search) {
		List<Site> topSitesRequirements = new ArrayList<>();
		String sqlReturnTopSitesRequirements = ""; //TODO write SQL query
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlReturnTopSitesRequirements);
		
		while(results.next()) {
			topSitesRequirements.add(mapRowToSite(results));
		}
		return topSitesRequirements;
	}
	
	public Campground addCampground(Campground newCampground) {
		String sqlInsertCampground = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, "
				+ "daily_fee) VALUES(?, ?, ?, ?, ?, ?)";
		newCampground.setCampgroundId(getNextCampgroundId());
		
		jdbcTemplate.update(sqlInsertCampground, newCampground.getCampgroundId(), newCampground.getParkId(), 
				newCampground.getCampName(), newCampground.getOpenFromMonth(), newCampground.getOpenToMonth(), 
				newCampground.getDailyFee());
		
		return newCampground;
	}
	
	private int getNextCampgroundId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('campground_campground_id_seq')");
		if(nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException ("Something went wrong looking for the next campground ID");
		}
	}
	
	private Site mapRowToSite(SqlRowSet results) {
		Site thisSite = new Site();
		thisSite.setSiteId(results.getLong("site_id"));
		thisSite.setCampgroundId(results.getInt("campground_id"));
		thisSite.setSiteNumber(results.getInt("site_number"));
		thisSite.setMaxOccupancy(results.getInt("max_occupancy"));
		thisSite.setAccessible(results.getBoolean("accessible"));
		thisSite.setMaxRVlength(results.getInt("max_rv_length"));
		thisSite.setUtilities(results.getBoolean("utilities"));
		return thisSite;
	}
}