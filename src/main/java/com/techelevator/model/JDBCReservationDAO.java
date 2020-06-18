package com.techelevator.model;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	private JDBCReservationDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public Reservation addReservation(long siteId, String name, LocalDate fromDate, LocalDate toDate, 
			LocalDate createDate) {
		String sqlInsertReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date " +
				"VALUES(?, ?, ?, ?, ?, ?)";
		
		Reservation newReservation = new Reservation();
		newReservation.setSiteId(siteId);
		newReservation.setName(name);
		newReservation.setFromDate(fromDate);
		newReservation.setToDate(toDate);
		newReservation.setCreateDate(createDate);
		newReservation.setReservationId(getNextReservationId());
		
		jdbcTemplate.update(sqlInsertReservation, newReservation.getReservationId(), newReservation.getSiteId(), 
				newReservation.getName(), newReservation.getFromDate(), newReservation.getToDate(), newReservation.getCreateDate());
		
		return newReservation;
	}
	
	private long getNextReservationId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException ("Something went wrong while getting an id for the new reservation");
		}
	}
	
}