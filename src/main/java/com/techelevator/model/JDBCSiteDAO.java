package com.techelevator.model;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	
	public Site addSite(Site newSite) {
		String sqlInsertSite = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, "
				+ "accessible, max_rv_length, utilities) VALUES(?, ?, ?, ?, ?, ?, ?)";
		newSite.setSiteId(getNextSiteId());
		
		jdbcTemplate.update(sqlInsertSite, newSite.getSiteId(), newSite.getCampgroundId(), newSite.getSiteNumber(),
				newSite.getMaxOccupancy(), newSite.isAccessible(), newSite.getMaxRVlength(), newSite.isUtilities());
		
		return newSite;
	}
	
	private long getNextSiteId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('site_site_id_seq')");
		if (nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException ("Something went wrong while getting an id for the new site");
		}
	}

}