package com.techelevator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;
import com.techelevator.model.CampgroundSearch;
import com.techelevator.model.JDBCCampgroundDAO;
import com.techelevator.model.JDBCParkDAO;
import com.techelevator.model.JDBCReservationDAO;
import com.techelevator.model.Park;
import com.techelevator.model.ParkDAO;
import com.techelevator.model.Reservation;
import com.techelevator.model.ReservationDAO;
import com.techelevator.model.Site;
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
		//his.menu = new Menu();
		parkDAO = new JDBCParkDAO(datasource);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
//		siteDAO = new JDBCSiteDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		
		
	}

	public void run() {
		//call method to display application banner
		while (true) {
//			runAllParksPage();
			System.out.println("Select a Park for details");
			System.out.println();
			Map<String, Park> parkmap = makeParkNamesOptionsList(); //helper method prints all other options
			System.out.println("Q) quit");
			System.out.println();
			String userInput = getUserInput();
			if (parkmap.containsKey(userInput) ) {  //&& userInputIsValid(userInput)
				Park specificPark = parkmap.get(userInput);
				runSpecificParkPage(specificPark);
			}
//			if (userInput.contains(s)) {
//				
//			} 
			else if (userInput.equalsIgnoreCase("q")) {
				
				System.exit(0);
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
//	public void runAllParksPage() {
//		System.out.println("Select a Park for details");
//		System.out.println();
//		Map<String, Park> parkmap = makeParkNamesOptionsList(); //helper method prints all other options
//		System.out.println("Q) quit");
//		System.out.println();
//		String userInput = getUserInput();
//		if (parkmap.containsKey(userInput) && userInputIsValid(userInput)) {
//			Park specificPark = parkmap.get(userInput);
//			runSpecificParkPage(specificPark);
//		}
//	}
	
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
		List<Campground> campList = parkDAO.returnAllCampgrounds(thisPark.getParkId());
		int maxLength = returnMaxLength(campList);
		System.out.println(thisPark.getParkName() + " National Park Campgrounds");
		System.out.println();
		System.out.println("Name" + tabFormatterTitle(maxLength) + "Opens\tCloses\tDaily Fee"); //TODO make method to make this look nice
		System.out.println();
		int parkId = thisPark.getParkId();
		Map<String, Campground> campMap = makeCampgroundsUserList(campList, maxLength); //prints list of campgrounds to user
		System.out.println();
		
		System.out.println("What would you like to do?");
		System.out.println("1) Search for reservation date availability");
		System.out.println("2) Return to " + thisPark.getParkName() + " National Park page"); //TODO test all these going back things
		String userInput = getUserInput();
		if(userInput.equals("1")) {
			runSearchCampsitesFromCamp(campMap);
		} //selecting 2 returns user to last layer
		
	}
	
	public void runParkwideReservationPage(Park thisPark) {
		System.out.println(thisPark.getParkName() + "National Park");
		System.out.println("Parkwide Searches");
		System.out.println();
		System.out.println("1) Search campsite availability");
		System.out.println("2) See all upcoming reservations");
		System.out.println("3) Return to Park page");
		String userInput = getUserInput();
		if(userInput.equals("1")) {
//			runParkwideSearchCampsiteAvailability(thisPark); //TODO write method
		}
		else if(userInput.equals("2")) {
//			runListAllUpcomingReservations();  //TODO write method 
		} //selecting "3" should return user to last layer
	}
	
	public List<Site> runSearchCampsitesFromCamp(Map<String, Campground> campMap) {
		System.out.println("Which campground? (Enter * to cancel search ___");
		String userCampground = getUserInput();
		int specificCampID = 0;
		if (campMap.containsKey(userCampground)) {
			Campground specificCamp = campMap.get(userCampground); //map contains park id
			specificCampID = specificCamp.getCampgroundId();
		}
		
		System.out.println("Desired arrival date? __/__/____");
		String userArrival = getUserInput();
		LocalDate arrivalDate = null;
		if (userInputIsValid()) {
			//convert user input to yyyy-MM-dd
			arrivalDate = LocalDate.parse(userArrival);	//check on this later
		}
		
		System.out.println("Desired departure date? __/__/____");
		String userDeparture = getUserInput();
		LocalDate departureDate = null;
		if () {
			//convert user input to appropriate format
			departureDate = LocalDate.parse(userDeparture);
		}
		
		CampgroundSearch campgroundSearch = new CampgroundSearch(arrivalDate, departureDate);
		campgroundSearch.setCampgroundId(specificCampID);
		List<Site> resultsList = campgroundDAO.returnTopAvailableSites(campgroundSearch);
		
		return  resultsList;
	}
	
//	public List<Site> runParkwideSearchCampsiteAvailability(Park thisPark){
//		
//		
//		return resultsList;
//	}
//	
//	public List<Reservation> runListAllUpcomingReservations(){
//		
//	}
	
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
			System.out.println(counter + ") " + parkName); //deleted "(" + before counter
		}
		return parkMap;
	}
	
	public Map<String, Campground> makeCampgroundsUserList(List<Campground> campList, int maxLength){
		Map<String, Campground> campMap = new HashMap<>();
		int counter = 0;
		String campName = "";
		for (Campground camp : campList) {
			campName = camp.getCampName();
			counter += 1;
			campMap.put(Integer.toString(counter), camp);
			System.out.println("(" + counter + ") " + campName + tabFormatter2(campName, maxLength) + camp.getOpenFromMonth() + "\t" + camp.getOpenToMonth() + "\t$" + camp.getDailyFee());
		}
		return campMap;
	}
	
	private int returnMaxLength(List<Campground> campList) {
		int maxLength = 0;
		for (int i = 0; i < campList.size(); i++) {
			if (maxLength < campList.get(i).getCampName().length()) {
				maxLength = campList.get(i).getCampName().length();
			}
		}
		return maxLength;
	}
	
	private String tabFormatterTitle(int maxLength) {
		
		int tabCount = 1;
		if ((maxLength - 4) % 8 == 0) {
			tabCount += ((maxLength - 4) / 8);
		} else {
			tabCount += ((maxLength - 4) / 8) + 1;
		}
		String tabs = "";
		while (tabCount > 0) {
			tabs = tabs + "\t";
			tabCount --;
		}
		return tabs;
	}
	
	private String tabFormatter2(String campName, int maxLength) {
		int tabCount = 0;
		if ((maxLength - 4) % 8 == 0) {
			tabCount += ((maxLength - 4) / 8);
		} else {
			tabCount += ((maxLength - 4) / 8) + 1;
		}
		String tabs = "";
		if (campName.length() > 12) {
			tabCount -= (((campName.length() - 4) / 8));
		}
		while (tabCount > 0) {
			tabs = tabs + "\t";
			tabCount --;
		}
		return tabs;
	}
	
	private String mapMMToMonth (String mM) {
		if (mM.equals("01")) {
			return "January";
		} else if (mM.equals("02")) {
			return "February";
		} else if (mM.equals("03")) {
			return "March";
		} else if (mM.equals("04")) {
			return "April";
		} else if (mM.equals("05")) {
			return "May";
		} else if (mM.equals("06")) {
			return "June";
		} else if (mM.equals("07")) {
			return "July";
		} else if (mM.equals("08")) {
			return "August";
		} else if (mM.equals("09")) {
			return "September";
		} else if (mM.equals("10")) {
			return "October";
		} else if (mM.equals("11")) {
			return "November";
		} else if (mM.equals("12")) {
			return "December";
		} else { return "DataNotAvailable";}
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




//public Map<String, Park> makeParkNamesOptionsList() {		//may need to add (List<Park> parkList)
//	List<Park> parkList = parkDAO.returnAllParks();
//	Map<String, Park> parkMap = new HashMap<>();
//	int counter = 0;
//	String parkName = "";
//	for (Park park : parkList) {
//		parkName = park.getParkName();
//		counter += 1;
//		parkMap.put(Integer.toString(counter), park);
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