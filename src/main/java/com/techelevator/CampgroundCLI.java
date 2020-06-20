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
		while (true) {
			runAllParksPage();
			if (userInput.contains(s)) {
				
			} else if (userInput.equalsIgnoreCase("q")) {
				//stop application from running
			}
		}
		
	}
	
	//user input methods
	public String getUserInput() {
		System.out.print("Please choose an option >> ");
		return new Scanner(System.in).nextLine();
	}
	
	public boolean userInputIsValid(String userInput) { //checks for all the weird things
		if() {
			return true;
		} 
		System.out.println("Invalid selection");
		return false;
	}
	
	
	//subpage methods
	public void runAllParksPage() {
		System.out.println("Select a Park for details");
		System.out.println();
		Map<String, Park> parkmap = makeParkNamesOptionsList(); //helper method prints all other options
		System.out.println("Q) quit");
		System.out.println();
		String userInput = getUserInput();
		if (parkmap.containsKey(userInput) && userInputIsValid(userInput)) {
			Park specificPark = parkmap.get(userInput);
			runSpecificParkPage(specificPark);
		}
	}
	
	public void runSpecificParkPage(Park thisPark) {
		System.out.println(thisPark.getParkName());
		System.out.println();
		System.out.print("Location:  ");
		System.out.println(thisPark.getLocation());
		System.out.print("Established:  ");
		System.out.println(thisPark.getEstablishDate());
		System.out.print("Area:  ");
		System.out.println(thisPark.getArea());
		System.out.print("Annual Visitors:  ");
		System.out.println(thisPark.getVisitors());
		System.out.println();
		System.out.println(thisPark.getDescription());
		System.out.println();
		
		System.out.println("What would you like to do?");
		System.out.println("1) View Campgrounds");
		System.out.println("2) View Parkwide Reservation Information");
		System.out.println("3) Return to All Parks");
		String userInput = getUserInput();
		if(userInput.equals("1")) {
			runCampgroundsPage(thisPark);
		}
		else if(userInput.equals("2")) {
			runParkwideReservationPage(thisPark);
		}
		
	}
	
	public void runCampgroundsPage(Park thisPark) {
		System.out.println(thisPark.getParkName() + " National Park Campgrounds");
		System.out.println();
	}
	
	public void runParkwideReservationPage(Park thisPark) {
		
	}
	
	//helper methods
	public Map<String, Park> makeParkNamesOptionsList() {		//may need to add (List<Park> parkList)
		List<Park> parkList = parkDAO.returnAllParks();
		Map<String, Park> parkMap = new HashMap<>();
		int counter = 0;
		String parkName = "";
		for (Park park : parkList) {
			parkName = park.getParkName();
			counter += 1;
			parkMap.put(Integer.toString(counter), park);
			System.out.println("(" + counter + ") " + parkName);
		}
		return parkMap;
	}
	
	
	
}

//Code Repo

//public void run() {
//	//call method to display application banner
//	Scanner scanner = new Scanner(System.in);
//	while (true) {	//main logic loop for menu; calls other methods
//		String userInput = scanner.nextLine();
//		System.out.println("Select a Park for details");
//		System.out.println();
//		makeParkNamesOptionsList();
//		System.out.println("Q) quit");
//		System.out.println();
//		//read in user input
//		if (userInput.contains(s)) {
//			//set a var to the value, then feed it into displayParkInfo()
//			displayParkInfo();
//			runParkLevelMenu();
//			//run the method below that corresponds
//		} else if (userInput.equalsIgnoreCase("q")) {
//			//stop application from running
//		}
//			scanner.close();
//	}
//	
//}

//public Map<Integer, Park> makeParkNamesOptionsList() {		//may need to add (List<Park> parkList)
//	List<Park> parkList = parkDAO.returnAllParks();
//	Map<Integer, Park> parkMap = new HashMap<>();
//	int counter = 0;
//	String parkName = "";
//	for (Park park : parkList) {
//		parkName = park.getParkName();
//		counter += 1;
//		parkMap.put(counter, park);
//		System.out.println("(" + counter + ") " + parkName);
//	}
//	return parkMap;
//}


//public void displayParkInfo() { //accepts a park_id, prints corresp info
//	System.out.println();
//	System.out.println();
//	System.out.println();
//	System.out.println();
//	System.out.println();
//	
//}
//
//public void runParkLevelMenu() {
//	if (userInput.equals("1")) {
//		runCampgroundsPage();
//	} else if (userInput.equals("2")) {
//		runParkWideSearchesPage();
//	} 
//	//should return to spot in run automatically for selection of 3
//}
//
//
//public void runCampgroundsPage() {
//	//prints out 
//}