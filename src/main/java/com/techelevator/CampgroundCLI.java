package com.techelevator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.JDBCCampgroundDAO;
import com.techelevator.model.JDBCParkDAO;
import com.techelevator.model.JDBCReservationDAO;
import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.ReservationDAO;
import com.techelevator.projects.view.Menu;

public class CampgroundCLI {
	
	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
//	private SiteDAO siteDAO;
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
		campgroundDAO = new JDBCCampgroundDAO(datasource);
//		siteDAO = new JDBCSiteDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		
		
	}

	public void run() {
		//call method to display application banner
		Scanner scanner = new Scanner(System.in);
		while (true) {	//main logic loop for menu; calls other methods
			String userInput = scanner.nextLine();
			System.out.println("Select a Park for details");
			System.out.println();
			makeParkNamesOptionsList();
			System.out.println("Q) quit");
			System.out.println();
			//read in user input
			if (userInput.contains(s)) {
				//set a var to the value, then feed it into displayParkInfo()
				displayParkInfo();
				runParkLevelMenu();
				//run the method below that corresponds
			} else if (userInput.equalsIgnoreCase("q")) {
				//stop application from running
			}
				scanner.close();
		}
		
	}
	
	public boolean userInputIsValid() {
		//if 
		return false;
	}
	
	public Map<Integer, Park> makeParkNamesOptionsList() {		//may need to add (List<Park> parkList)
		List<Park> parkList = parkDAO.returnAllParks();
		Map<Integer, Park> parkMap = new HashMap<>();
		int counter = 0;
		String parkName = "";
		for (Park park : parkList) {
			parkName = park.getParkName();
			counter += 1;
			parkMap.put(counter, park);
			System.out.println("(" + counter + ") " + parkName);
		}
		return parkMap;
	}
	
	public void displayParkInfo() { //accepts a park_id, prints corresp info
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
	}
	
	public void runParkLevelMenu() {
		if (userInput.equals("1")) {
			runCampgroundsPage();
		} else if (userInput.equals("2")) {
			runParkWideSearchesPage();
		} 
		//should return to spot in run automatically for selection of 3
	}
	
	
	public void runCampgroundsPage() {
		//prints out 
	}
	
}


