package com.techelevator;

import java.time.LocalDate;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.JDBCCampgroundDAO;
import com.techelevator.model.JDBCParkDAO;
import com.techelevator.model.JDBCReservationDAO;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.ReservationDAO;



public class CampgroundCLITest {
	
	CampgroundCLI testCLI;
//	ParkDAO testParkDAO;
//	CampgroundDAO testCampgroundDAO;
//	ReservationDAO testReservationDAO;
	
	@Before
	public void setup() {
		BasicDataSource testDataSource = new BasicDataSource();
		testDataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		testDataSource.setUsername("postgres");
		testDataSource.setPassword("postgres1");
		
		this.testCLI = new CampgroundCLI(testDataSource);
		
//		testParkDAO = new JDBCParkDAO(testDataSource);
//		testCampgroundDAO = new JDBCCampgroundDAO(testDataSource);
//		testReservationDAO = new JDBCReservationDAO(testDataSource);
	}
	
	
	@Test 
	public void isDateValid_past_date_returns_false(){
		String input = (LocalDate.now().minusDays(2)).toString();
		boolean expectedOutput = false;
		
		boolean actualOutput = testCLI.isDateValid(input);
		Assert.assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
	public void isDateValid_null_returns_false() {
		String input = null;
		boolean expectedOutput = false;
		
		boolean actualOutput = testCLI.isDateValid(input);
		Assert.assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
	public void isDateValid_negative_date_returns_false() {
		int currentYear = LocalDate.now().getYear();
		String input = "-01/01/" + (currentYear + 1);
		boolean expectedOutput = false;
		
		boolean actualOutput = testCLI.isDateValid(input);
		Assert.assertEquals(expectedOutput, actualOutput);
	}
	
}
