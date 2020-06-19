package com.techelevator;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	
	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;
	
	

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		// create your DAOs here
		this.menu = new Menu();
		parkDAO = new JDBCParkDAO(datasource);
		campgroundDAO = new JDBC
		
	}

	public void run() {
		//call method to display banner
		
		while (true) {	//main logic loop for menu; calls other methods
			//display parks page
//			if (//userinput == any valid park number) {
//				//run the method below that corresponds
//			} else if (//userinput == Q) {
//				//stop application from running
//			}
		}
	}
	
	
	
	
}
