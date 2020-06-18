package com.techelevator.model;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	public Reservation addReservation(Reservation newReservation) {
		String sqlInsertReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date) " +
				"VALUES(?, ?, ?, ?, ?, ?)";
		
		newReservation.setReservationId(getNextReservationId());
		
		jdbcTemplate.update(sqlInsertReservation, newReservation.getReservationId(), newReservation.getSiteId(), 
				newReservation.getName(), newReservation.getFromDate(), newReservation.getToDate(), newReservation.getCreateDate());
		
		return newReservation;
	}
	
	private long getNextReservationId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException ("Something went wrong while getting an id for the new reservation");
		}
	}
	
	public Reservation getReservationById(Long reservationId) {
		Reservation foundReservation = new Reservation();
		String sqlGetReservationByReservationId = "SELECT reservation_id, site_id, name, from_date, to_date, create_date " +
			"FROM reservation WHERE reservation_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReservationByReservationId, reservationId);
		
		if(results.next()) {
			foundReservation = mapRowToReservation(results);
		}
		return foundReservation;
	}
	
	
	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation someReservation = new Reservation();
		someReservation.setReservationId(results.getLong("reservation_id"));
		someReservation.setSiteId(results.getLong("site_id"));
		someReservation.setName(results.getString("name"));
		someReservation.setFromDate(results.getDate("from_date").toLocalDate());
		someReservation.setToDate(results.getDate("to_date").toLocalDate());
		someReservation.setCreateDate(results.getDate("create_date").toLocalDate());
		return someReservation;
	}
	
}