package com.techelevator.model;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	@Override
	public List<Site> returnTopAvailableSites(CampgroundSearch search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> returnTopSitesRequirements(AdvancedSearch search) {
		// TODO Auto-generated method stub
		return null;
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
	

}