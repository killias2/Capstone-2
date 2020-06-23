package com.techelevator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.AdvancedSearch;
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
import com.techelevator.model.ReservationSearch;
import com.techelevator.model.Site;
import com.techelevator.projects.view.Menu;

public class CampgroundCLI {
	
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
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
		reservationDAO = new JDBCReservationDAO(datasource);
		
		
	}

	public void run() {
		//call method to display application banner
		while (true) {
			System.out.println("Select a Park for details");
			System.out.println();
			Map<String, Park> parkmap = makeParkNamesOptionsList(); //helper method prints all other options
			System.out.println("Q) quit");
			System.out.println();
			String userInput = getUserInput();
			if (parkmap.containsKey(userInput) ) {
				Park specificPark = parkmap.get(userInput);
				runSpecificParkPage(specificPark);
			}
			else if (userInput.equalsIgnoreCase("q")) {
				System.exit(0);
			} 
			else {
				System.out.println("Please choose from the available options");
			}
		}
		
	}
	
	//user input methods
	public String getUserInput() {
		System.out.print("Please choose an option >> ");
		String input = new Scanner(System.in).nextLine();
		return input;
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
		boolean inputchecker = false;
		while (inputchecker == false) {
			if(userInput.equals("1")) {
				inputchecker = true;
				runCampgroundsPage(thisPark);
			}
			else if(userInput.equals("2")) {
				inputchecker = true;
				runParkwideReservationPage(thisPark);
			} else if (userInput.equals("3")) {
				inputchecker = true;
				run();
			} else {
				System.out.println("");
				System.out.println("This is not a valid menu option");
				System.out.println("Please check the menu and try again. Thank you!");
				System.out.println("");
				runParkwideReservationPage(thisPark);
			}
		}
		
	}
	
	public void runCampgroundsPage(Park thisPark) {
		List<Campground> campList = parkDAO.returnAllCampgrounds(thisPark.getParkId());
		int maxLength = returnMaxLength(campList);
		int monthTab = returnMonthTab(campList);
		System.out.println(thisPark.getParkName() + " National Park Campgrounds");
		System.out.println();
		System.out.println("Name" + tabFormatterTitle(maxLength) + "Opens" + tabFormatterMonth(monthTab) + "Closes\tDaily Fee"); //TODO make method to make this look nice
		System.out.println();
		int parkId = thisPark.getParkId();
		Map<String, Campground> campMap = makeCampgroundsUserList(campList, maxLength, monthTab); //prints list of campgrounds to user
		System.out.println();
		
		System.out.println("What would you like to do?");
		System.out.println("1) Search for reservation date availability");
		System.out.println("2) Return to " + thisPark.getParkName() + " National Park page"); //TODO test all these going back things
		boolean inputchecker = false;
		while (inputchecker == false) {
			String userInput = getUserInput();
			if(userInput.equals("1")) {
				inputchecker = true;
				runSearchCampsitesFromCamp(campMap, thisPark, maxLength);
//				List<Site> searchResults = runSearchCampsitesFromCamp(campMap, thisPark);
//				if (searchResults.size() > 0) {
//					System.out.println("Results:");
//					System.out.println();
//					System.out.println("Site No., Max Occup., Acc?, Max RV Length, Utility, Cost"); //TODO clean up display
//					Map <String, Site> campSitesList = makeCampSitesList(searchResults);  
//					
//					runSearchCampsitesFromCamp(campSitesList, thisPark);
//				} else {
//					System.out.println("There are no available campsites that match the dates and/or search parameters.");
//				}
				
			}
			else if(userInput.equals("2")) {
				inputchecker = true;
				runSpecificParkPage(thisPark);
			}
			else {
				System.out.println("Please check the available options and try again");
			}
		}
		
	}
	
	public void runParkwideReservationPage(Park thisPark) {		
		System.out.println(thisPark.getParkName() + " National Park");
		System.out.println("Parkwide Searches");
		System.out.println();
		System.out.println("1) Search campsite availability");
		System.out.println("2) See all upcoming reservations");
		System.out.println("3) Return to Park page");
		String userInput = "";
		userInput = getUserInput();
		if(userInput.equals("1")) {
			runParkwideSearchCampsiteAvailability(thisPark);
		}
		else if(userInput.equals("2")) {
			runListAllUpcomingReservations(thisPark); 
		} else if (userInput.equals("3")) {
			runSpecificParkPage(thisPark);
		} else {
			System.out.println("");
			System.out.println("This is not a valid menu option");
			System.out.println("Please check the menu and try again. Thank you!");
			System.out.println("");
			runParkwideReservationPage(thisPark);
		}
	}
	
	public void runParkwideSearchCampsiteAvailability(Park thisPark) {
		LocalDate fromDate = (LocalDate.now());
		LocalDate toDate = (LocalDate.now());
		boolean inputchecker = false;
		while (inputchecker == false) {
			System.out.println("What is the arrival date? __/__/____");
			String arrivalDateInput = getUserInput();
			if (isDateValid(arrivalDateInput) == false) {
				System.out.println("");
				System.out.println("I'm sorry, but please check to ensure that your date is valid");
				System.out.println("");
			}
			else if (isDateValid(arrivalDateInput) == true) {
				inputchecker = true;
				fromDate = LocalDate.parse(arrivalDateInput, DateTimeFormatter.ofPattern("MM/dd/uuuu"));
			}
		}
		inputchecker = false;
		while (inputchecker == false) {
			System.out.println("What is the departure date? __/__/____");
			String departureDateInput = getUserInput();
			if (isDateValid(departureDateInput) == false) {
				System.out.println("");
				System.out.println("I'm sorry, but please check to ensure that your date is valid");
				System.out.println("");
			}
			else {
				toDate = LocalDate.parse(departureDateInput, DateTimeFormatter.ofPattern("MM/dd/uuuu"));
				if (toDate.isBefore(fromDate)) {
					System.out.println("");
					System.out.println("I'm sorry, but your departure date cannot precede your arrival date");
					System.out.println("");
				}
				else if (isDateValid(departureDateInput) == true && (!toDate.isBefore(fromDate))) {
					inputchecker = true;
				}
			}
		}
		inputchecker = false;
		while (inputchecker == false) {
			System.out.println("");
			System.out.println("Would you like to enter additional search parameters? Y/N");
			System.out.println("");
			String ynInput = getUserInput();
			if (ynInput.toUpperCase().equals("N")) {
				inputchecker = true;
				System.out.println("Running your search....");
				runParkwideSearchCampsiteAvailabilityBasic(thisPark, fromDate, toDate);
			}
			else if (ynInput.toUpperCase().equals("Y")) {
				inputchecker = true;
				runParkwideSearchCampsiteAvailabilityAdvanced(thisPark, fromDate, toDate);
			} else {
				System.out.println("Please choose Y or N, thank you!");
			}
		}
	}
	
	public void runParkwideSearchCampsiteAvailabilityBasic(Park thisPark, LocalDate fromDate, LocalDate toDate) {
		ReservationSearch search = new ReservationSearch(fromDate, toDate);
		search.setParkId(thisPark.getParkId());
		List<Site> siteList = parkDAO.returnAllAvailableSites(search);
		long resLength = ChronoUnit.DAYS.between(fromDate, toDate);
		boolean inputchecker = false;
		if (siteList.isEmpty()) {
			System.out.println("");
			System.out.println("No Results Match Your Search Criteria");
			System.out.println("Please Try Another Search");
			System.out.println("");
			runParkwideSearchCampsiteAvailability(thisPark);
		}
		while (inputchecker == false) {			
			System.out.println("Results Matching Your Search Criteria");
			int maxLength = returnMaxLengthSite(siteList);
			System.out.println("Campground" + tabFormatterTitleParkwideSites(maxLength) + "Site ID\tSite No.\tMax Occup.\tAccessible\tRV Len\tUtility\tCost");
			Map<String, Site> localMap = makeParkwideResList(siteList, maxLength, resLength);
			System.out.println("Which site (Enter Site ID) should be reserved (enter 0 to cancel)?");
			String input = getUserInput();
			if (input.equals("0")) {
				inputchecker = true;
				runParkwideReservationPage(thisPark);
			}
			else {
				if (localMap.containsKey(input)){
					inputchecker = true;
					System.out.println("What name should the reservation be made under? (80 characters max)");
					String reservationName = getUserInput();
					Reservation newReservation = new Reservation();
					newReservation.setFromDate(fromDate);
					newReservation.setToDate(toDate);
					newReservation.setSiteId(localMap.get(input).getSiteId());
					newReservation.setReservationName(reservationName);
					newReservation.setCreateDate(LocalDate.now());
					reservationDAO.addReservation(newReservation);				
					inputchecker = true;
					runParkwideReservationPage(thisPark);
					System.out.println("The reservation has been made and the confirmation id is " + newReservation.getReservationId());
					
				} else {
					System.out.println("");
					System.out.println("I'm sorry, you did not select an available site, please try again.");
					System.out.println("");
				}
			}
		}
	}
	
	public void runParkwideSearchCampsiteAvailabilityAdvanced(Park thisPark, LocalDate fromDate, LocalDate toDate) {
		boolean inputchecker = false;
		int searchMaxOccupancy = 0;
		boolean isAccessible = false;
		int searchMaxRVLength = 0;
		boolean isUtilities = false;
		while (inputchecker == false) {		
			System.out.println("Number of people in your party: __");
			String input = getUserInput();
			try {
				if (Integer.parseInt(input) < 0) {
					System.out.println("This is not a valid choice, please try again.");
				}
				else {
					inputchecker = true;
					searchMaxOccupancy = Integer.parseInt(input);
				}
			} catch(NumberFormatException e) {
				System.out.println("This is not a valid choice, please try again.");
			}
		}
		inputchecker = false;
		while (inputchecker == false) {		
			System.out.println("Wheelchair accessibility required (Y/N)? __");
			String input = getUserInput();
			if (input.toUpperCase().equals("Y")) {
				inputchecker = true;
				isAccessible = true;
			} else if (input.toUpperCase().equals("N")){
				inputchecker = true;
				isAccessible = false;
			} else {
				System.out.println("Please choose Y or N, thank you!");
			}
		}
		inputchecker = false;
		while (inputchecker == false) {
			System.out.println("Length of RV (enter 0 if not bringing an RV): __");
			String input = getUserInput();
			try {
				if (Integer.parseInt(input) < 0) {
					System.out.println("This is not a valid choice, please try again.");
				}
				else {
					inputchecker = true;
					searchMaxRVLength = Integer.parseInt(input);
				}
			} catch(NumberFormatException e) {
				System.out.println("This is not a valid choice, please try again.");
			}
		}
		inputchecker = false;
		while (inputchecker == false) {		
			System.out.println("Utility hookup required (Y/N)? __");
			String input = getUserInput();
			if (input.toUpperCase().equals("Y")) {
				inputchecker = true;
				isUtilities = true;
			} else if (input.toUpperCase().equals("N")){
				inputchecker = true;
				isUtilities = false;
			} else {
				System.out.println("Please choose Y or N, thank you!");
			}
		}
		AdvancedSearch search = new AdvancedSearch(fromDate, toDate, searchMaxOccupancy, isAccessible, searchMaxRVLength, isUtilities);
		search.setParkId(thisPark.getParkId());
		List<Site> siteList = parkDAO.returnAllAvailableSitesAdvanced(search);
		long resLength = ChronoUnit.DAYS.between(fromDate, toDate);
		inputchecker = false;
		if (siteList.isEmpty()) {
			System.out.println("");
			System.out.println("No Results Match Your Search Criteria");
			System.out.println("Please Try Another Search");
			System.out.println("");
			runParkwideSearchCampsiteAvailability(thisPark);
		}
		while (inputchecker == false) {			
			System.out.println("Results Matching Your Search Criteria");
			int maxLength = returnMaxLengthSite(siteList);
			System.out.println("Campground" + tabFormatterTitleParkwideSites(maxLength) + "Site ID\tSite No.\tMax Occup.\tAccessible\tRV Len\tUtility\tCost");
			Map<String, Site> localMap = makeParkwideResList(siteList, maxLength, resLength);
			System.out.println("Which site (Enter Site ID) should be reserved (enter 0 to cancel)?");
			String input = getUserInput();
			if (input.equals("0")) {
				inputchecker = true;
				runParkwideReservationPage(thisPark);
			}
			else {
				if (localMap.containsKey(input)){
					inputchecker = true;
					System.out.println("What name should the reservation be made under? (80 characters max)");
					String reservationName = getUserInput();
					Reservation newReservation = new Reservation();
					newReservation.setFromDate(fromDate);
					newReservation.setToDate(toDate);
					newReservation.setSiteId(localMap.get(input).getSiteId());
					newReservation.setReservationName(reservationName);
					newReservation.setCreateDate(LocalDate.now());
					reservationDAO.addReservation(newReservation);				
					inputchecker = true;
					runParkwideReservationPage(thisPark);
					System.out.println("The reservation has been made and the confirmation id is " + newReservation.getReservationId());
					
				} else {
					System.out.println("");
					System.out.println("I'm sorry, you did not select an available site, please try again.");
					System.out.println("");
				}
			}
		}
	}
		
	private void runListAllUpcomingReservations(Park thisPark) {
		List<Reservation> resList = parkDAO.returnAllReservationsNext30Days(thisPark.getParkId());
		if (resList.isEmpty()) {
			System.out.println("");
			System.out.println("There are no upcoming reservations at " + thisPark.getParkName());
			System.out.println("");
			runParkwideReservationPage(thisPark);
		}
		System.out.println("Here is a list of all reservations at " + thisPark.getParkName());
		System.out.println("Campground\tSite No.\tFrom Date\tTo Date\t\tCreate Date\tReservation Name");
		int maxLength = returnMaxLengthRes(resList);
		for (int i = 0; i < resList.size(); i++) {
			Reservation thisRes = resList.get(i);
			System.out.println(thisRes.getCampName() + tabFormatterParkwideSites(maxLength, thisRes.getCampName()) + thisRes.getSiteNumber() + "\t\t" + 
			thisRes.getFromDate() + "\t" + thisRes.getToDate() + "\t" + thisRes.getCreateDate() + "\t" 
			+ thisRes.getReservationName());
		}
		runParkwideReservationPage(thisPark);
	}
	
	//v Layer E
	public List<Site> runSearchCampsitesFromCamp(Map<String, Campground> campMap, Park thisPark, int maxLength) {
		List<Site> resultsList = new ArrayList<>();
		System.out.println("Which campground? (Enter * to cancel search ___");
		String userCampground = getUserInput();
		int specificCampID = 0;
		Campground specificCamp = new Campground();
		boolean inputchecker = false;
		while (inputchecker == false)
		if (campMap.containsKey(userCampground)) {
			specificCamp = campMap.get(userCampground); //map contains park id
			specificCampID = specificCamp.getCampgroundId();
			inputchecker = true;
		}
		else {
			System.out.println("Please choose from the available campgrounds");
		}
		
		LocalDate fromDate = (LocalDate.now());
		LocalDate toDate = (LocalDate.now());
		inputchecker = false;
		while (inputchecker == false) {
			System.out.println("What is the arrival date? __/__/____");
			String arrivalDateInput = getUserInput();
			if (isDateValid(arrivalDateInput) == false) {
				System.out.println("I'm sorry, but please check to ensure that your date is valid");
			}
			else if (isDateValid(arrivalDateInput) == true) {
				inputchecker = true;
				fromDate = LocalDate.parse(arrivalDateInput, DateTimeFormatter.ofPattern("MM/dd/uuuu"));
			}
		}
		inputchecker = false;
		while (inputchecker == false) {
			System.out.println("What is the departure date? __/__/____");
			String departureDateInput = getUserInput();
			if (isDateValid(departureDateInput) == false) {
			System.out.println("I'm sorry, but please check to ensure that your date is valid");
			}
			else {
				toDate = LocalDate.parse(departureDateInput, DateTimeFormatter.ofPattern("MM/dd/uuuu"));
				if (toDate.isBefore(fromDate)) {
					System.out.println("");
					System.out.println("I'm sorry, but your departure date cannot precede your arrival date");
					System.out.println("");
				}
				else if (isDateValid(departureDateInput) == true && (!toDate.isBefore(fromDate))) {
					inputchecker = true;
				}
			}
		}
		System.out.println("Would you like to enter additional search parameters? Y/N");
		String runAdvSearchInput = getUserInput();
		inputchecker = false;
		while (inputchecker == false) {
			if (runAdvSearchInput.equalsIgnoreCase("N")) {
				System.out.println("Running your search....");
				runSearchFromCampground(fromDate, toDate, specificCamp, campMap, thisPark, maxLength);
			}
			else if (runAdvSearchInput.toUpperCase().equals("Y")) {
				inputchecker = true;
				runAdvSearchFromCampground(fromDate, toDate, specificCamp, campMap, thisPark, maxLength);
			} else {
				System.out.println("Please choose Y or N, thank you!");
			}
		}
		return  resultsList;
	}
	
	public void runSearchFromCampground(LocalDate fromDate, LocalDate toDate, Campground specificCamp, Map<String, Campground> campMap, Park thisPark, int maxLength) {
		CampgroundSearch search = new CampgroundSearch(fromDate, toDate);
		search.setCampgroundId(specificCamp.getCampgroundId());
		List<Site> siteList = campgroundDAO.returnTopAvailableSites(search);
		long resLength = ChronoUnit.DAYS.between(fromDate, toDate);
		boolean inputchecker = false;
		if (siteList.isEmpty()) {
			System.out.println("");
			System.out.println("No Results Match Your Search Criteria");
			System.out.println("Please Try Another Search");
			System.out.println("");
			runSearchCampsitesFromCamp(campMap, thisPark, maxLength);
		}
		while (inputchecker == false) {			
			System.out.println("Results Matching Your Search Criteria");
			System.out.println("Campground" + tabFormatterTitleParkwideSites(maxLength) + "Site ID\tSite No.\tMax Occup.\tAccessible\tRV Len\tUtility\tCost");
			Map<String, Site> localMap = makeCampSitesList(siteList);
			System.out.println("Which site (Enter Site ID) should be reserved (enter 0 to cancel)?");
			String input = getUserInput();
			if (input.equals("0")) {
				inputchecker = true;
				runSearchCampsitesFromCamp(campMap, thisPark, maxLength);
			}
			else {
				if (localMap.containsKey(input)){
					inputchecker = true;
					System.out.println("What name should the reservation be made under? (80 characters max)");
					String reservationName = getUserInput();
					Reservation newReservation = new Reservation();
					newReservation.setFromDate(fromDate);
					newReservation.setToDate(toDate);
					newReservation.setSiteId(localMap.get(input).getSiteId());
					newReservation.setReservationName(reservationName);
					newReservation.setCreateDate(LocalDate.now());
					reservationDAO.addReservation(newReservation);				
					inputchecker = true;
					System.out.println("The reservation has been made and the confirmation id is " + newReservation.getReservationId());
					runSearchCampsitesFromCamp(campMap, thisPark, maxLength);
				} else {
					System.out.println("");
					System.out.println("I'm sorry, you did not select an available site, please try again.");
					System.out.println("");
				}
			}
		}
	}
	
	public List<Site> runAdvSearchFromCampground(LocalDate arrivalDate, LocalDate departureDate, Campground specificCamp, Map<String, Campground> campMap, Park thisPark, int maxLength) {
		List<Site> advResultsList = new ArrayList<>();
		boolean inputchecker = false;
		int searchMaxOccupancy = 0;
		boolean isAccessible = false;
		int searchMaxRVLength = 0;
		boolean isUtilities = false;
		
		while (inputchecker == false) {		
			System.out.println("Number of people in your party: __");
			String input = getUserInput();
			try {
				if (Integer.parseInt(input) < 0) {
					System.out.println("This is not a valid choice, please try again.");
				}
				else {
					inputchecker = true;
					searchMaxOccupancy = Integer.parseInt(input);
				}
			} catch(NumberFormatException e) {
				System.out.println("This is not a valid choice, please try again.");
			}
		}
		inputchecker = false;
		while (inputchecker == false) {		
			System.out.println("Wheelchair accessibility required (Y/N)? __");
			String input = getUserInput();
			if (input.toUpperCase().equals("Y")) {
				inputchecker = true;
				isAccessible = true;
			} else if (input.toUpperCase().equals("N")){
				inputchecker = true;
				isAccessible = false;
			} else {
				System.out.println("Please choose Y or N, thank you!");
			}
		}
		inputchecker = false;
		while (inputchecker == false) {
			System.out.println("Length of RV (enter 0 if not bringing an RV): __");
			String input = getUserInput();
			try {
				if (Integer.parseInt(input) < 0) {
					System.out.println("This is not a valid choice, please try again.");
				}
				else {
					inputchecker = true;
					searchMaxRVLength = Integer.parseInt(input);
				}
			} catch(NumberFormatException e) {
				System.out.println("This is not a valid choice, please try again.");
			}
		}
		inputchecker = false;
		while (inputchecker == false) {		
			System.out.println("Utility hookup required (Y/N)? __");
			String input = getUserInput();
			if (input.toUpperCase().equals("Y")) {
				inputchecker = true;
				isUtilities = true;
			} else if (input.toUpperCase().equals("N")){
				inputchecker = true;
				isUtilities = false;
			} else {
				System.out.println("Please choose Y or N, thank you!");
			}
		}
		AdvancedSearch campAdvSearch = new AdvancedSearch(arrivalDate, departureDate, searchMaxOccupancy, isAccessible, searchMaxRVLength, isUtilities);
//		search.setParkId(thisPark.getParkId());
//		List<Site> siteList = parkDAO.returnAllAvailableSitesAdvanced(search);
		campAdvSearch.setCampgroundId(specificCamp.getCampgroundId());
		advResultsList = campgroundDAO.returnTopSitesRequirements(campAdvSearch);
		
		long resLength = ChronoUnit.DAYS.between(arrivalDate, departureDate);
		inputchecker = false;
		if (advResultsList.isEmpty()) {
			System.out.println("");
			System.out.println("No Results Match Your Search Criteria");
			System.out.println("Please Try Another Search");
			System.out.println("");
			runSearchCampsitesFromCamp(campMap, thisPark, maxLength);
		}
		while (inputchecker == false) {			
			System.out.println("Results Matching Your Search Criteria");
			System.out.println("Campground" + tabFormatterTitleParkwideSites(maxLength) + "Site ID\tSite No.\tMax Occup.\tAccessible\tRV Len\tUtility\tCost");
			Map<String, Site> localMap = makeCampSitesList(advResultsList);
			System.out.println("Which site (Enter Site ID) should be reserved (enter 0 to cancel)?");
			String input = getUserInput();
			if (input.equals("0")) {
				inputchecker = true;
				runCampgroundsPage(thisPark);//TODO
			}
			else {
				if (localMap.containsKey(input)){
					inputchecker = true;
					System.out.println("What name should the reservation be made under? (80 characters max)");
					String reservationName = getUserInput();
					Reservation newReservation = new Reservation();
					newReservation.setFromDate(arrivalDate);
					newReservation.setToDate(departureDate);
					newReservation.setSiteId(localMap.get(input).getSiteId());
					newReservation.setReservationName(reservationName);
					newReservation.setCreateDate(LocalDate.now());
					reservationDAO.addReservation(newReservation);				
					inputchecker = true;
//					runCampgroundsPage(thisPark);
					System.out.println("The reservation has been made and the confirmation id is " + newReservation.getReservationId());
					runSearchCampsitesFromCamp(campMap, thisPark, maxLength);
				} else {
					System.out.println("");
					System.out.println("I'm sorry, you did not select an available site, please try again.");
					System.out.println("");
				}
			}
		}
	

		return advResultsList;
	}
	
//	public String makeReservation(Map<String, Site> searchResults) { //TODO: write method
//		boolean isReserving = true;
//		Reservation newReservation = new Reservation();
//		while (isReserving) {
//			System.out.println("Which site would you like to reserve (enter 0 to cancel)?  __");
//			String siteNumberInput = getUserInput();
//			if(siteNumberInput.equals("0")) {
////				isReserving = false;
//				return "Reservation Canceled";
//			} else if (searchResults.containsKey(siteNumberInput)) {  //might need to 
//				
//			} 
//			System.out.println("Enter your name __");
//			String nameInput = getUserInput();
//			
//			
//		}
//		
//		return "";
//	}
	
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
	public boolean isDateValid(String inputDate) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US);
		try {
			LocalDate.parse(inputDate, dateFormatter);
		} catch (DateTimeParseException e) {
			return false;
		}
		if (LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("MM/dd/uuuu")).isBefore(LocalDate.now())) {
			return false;
		} 
		if (LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("MM/dd/uuuu")).isAfter(LocalDate.now().plusYears(2))){
			return false;
		}
		else {
			return true;
		}
	}
	
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
	
//	public Map<String, Campground> makeCampgroundsUserList(int parkId, List<Campground> campList){
//		Map<String, Campground> campMap = new HashMap<>();
//		int counter = 0;
//		String campName = "";
//		for (Campground camp : campList) {
//			campName = camp.getCampName();
//			counter += 1;
//			campMap.put(Integer.toString(counter), camp);
//			System.out.println("(" + counter + ") " + campName + tabFormatter2(campName, maxLength) + mapMMToMonth(camp.getOpenFromMonth()) + tabFormatterMonth(monthTab) + mapMMToMonth(camp.getOpenToMonth()) + "\t$" + camp.getDailyFee() + ".00");
//		}
//		return campMap;
//	}
	
	public Map<String, Campground> makeCampgroundsUserList(List<Campground> campList, int maxLength, int monthTab){
		Map<String, Campground> campMap = new HashMap<>();
		int counter = 0;
		String campName = "";
		for (Campground camp : campList) {
			campName = camp.getCampName();
			counter += 1;
			campMap.put(Integer.toString(counter), camp);
			System.out.println("(" + counter + ") " + campName + tabFormatter2(campName, maxLength) + mapMMToMonth(camp.getOpenFromMonth()) + tabFormatterMonth(monthTab) + mapMMToMonth(camp.getOpenToMonth()) + "\t$" + camp.getDailyFee() + ".00");
		}
		return campMap;
	}
	
	public Map<String, Site> makeParkwideResList(List<Site> siteList, int maxLength, long resLength){
		Map<String, Site> siteMap = new HashMap<>();
		String siteName = "";
		for (Site site : siteList) {
			siteName = site.getCampName();
			siteMap.put(Long.toString(site.getSiteId()), site);
			System.out.println(siteName + tabFormatterParkwideSites(maxLength, siteName) + site.getSiteId() + "\t" + + site.getSiteNumber() + "\t\t" + site.getMaxOccupancy()
			+ "\t\t" + site.isAccessible() + "\t\t" + site.getMaxRVlength() + "\t" + site.isUtilities() + "\t$" + (site.getDailyFee() * (resLength + 1)) + ".00");
		}
		return siteMap;
	}
	
	public Map<String, Site> makeCampSitesList(List<Site> results) {
		Map<String, Site> siteMap = new HashMap<>();
		//String siteName = "";
		int siteNumber = 0;
		for (Site result : results) { //TODO write method
			siteNumber = result.getSiteNumber();
			siteMap.put(Integer.toString(siteNumber), result);
			System.out.println(siteNumber + "," + result.getMaxOccupancy() + ", " + result.isAccessible() + ", " + result.getMaxRVlength() + ", " + result.isUtilities() + ", " + result.getDailyFee());  //TODO calc total cost and replace daily fee w/ it
		}
		return siteMap;
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
	
	private int returnMaxLengthSite(List<Site> siteList) {
		int maxLength = 0;
		for (int i = 0; i < siteList.size(); i++) {
			if (maxLength < siteList.get(i).getCampName().length()) {
				maxLength = siteList.get(i).getCampName().length();
			}
		}
		return maxLength;
	}
	
	private int returnMaxLengthRes(List<Reservation> resList) {
		int maxLength = 0;
		for (int i = 0; i < resList.size(); i++) {
			if (maxLength < resList.get(i).getCampName().length()) {
				maxLength = resList.get(i).getCampName().length();
			}
		}
		return maxLength;
	}
	
	
	private int returnMonthTab(List<Campground> campList) {
		int tabCount = 1;
		for (int i = 0; i < campList.size(); i++) {
			if (campList.get(i).getOpenFromMonth().equals("09")){
				tabCount = 2;
			}
		}
		return tabCount;
	}
	
	private String tabFormatterTitle(int maxLength) {
		
		int tabCount = 1;
		if (maxLength < 4) {
			tabCount = 1;
		} else if ((maxLength - 4) % 8 == 0) {
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
		if (maxLength < 4) {
			tabCount = 1;
		} else if ((maxLength - 4) % 8 == 0) {
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
	
	private String tabFormatterMonth(int monthTab) {
		String tabs = "";
		while (monthTab > 0) {
			tabs = tabs + "\t";
			monthTab --;
		}
		return tabs;
	}
	
	private String tabFormatterTitleParkwideSites(int maxLength) {
		
		int tabCount = 0;
		if (maxLength < 9) {
			tabCount = 0;
		} 
		else {
			tabCount += (maxLength) / 8;
		}
		String tabs = "";
		while (tabCount > 0) {
			tabs = tabs + "\t";
			tabCount --;
		}
		return tabs;
	}
	
	private String tabFormatterParkwideSites(int maxLength, String campName) {
		int tabCount = 0;
		if (maxLength < 9) {
			tabCount = 0;
		} else if (maxLength % 8 == 0) {
			tabCount += (maxLength / 8);
		} else {
			tabCount += ((maxLength) / 8) + 1;
		}
		String tabs = "";
		if (campName.length() > 8) {
			tabCount -= (campName.length() / 8);
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



//System.out.println("Desired arrival date? __/__/____");
//String userArrival = getUserInput();
//LocalDate arrivalDate = null;
//if (userInputIsValid()) {
//	//convert user input to yyyy-MM-dd
//	arrivalDate = LocalDate.parse(userArrival);	//check on this later
//}
//
//System.out.println("Desired departure date? __/__/____");
//String userDeparture = getUserInput();
//LocalDate departureDate = null;
//if () {
//	//convert user input to appropriate format
//	departureDate = LocalDate.parse(userDeparture);
//}