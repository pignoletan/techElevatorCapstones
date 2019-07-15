package com.techelevator;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

import com.techelevator.campground.Campground;
import com.techelevator.campground.JDBCCampgroundDAO;
import com.techelevator.park.JDBCParkDAO;
import com.techelevator.park.Park;
import com.techelevator.reservation.JDBCReservationDAO;
import com.techelevator.reservation.Reservation;
import com.techelevator.site.JDBCSiteDAO;
import com.techelevator.site.Site;
import com.techelevator.view.Menu;
import java.time.temporal.ChronoUnit;

public class CampgroundCLI {

	private static final String BREAK_LINE = "-----------------------------------";
	private static final String MAIN_MENU_GREETING = "Select a Park for Further Details | ";
	private static final String QUIT_MENU = "quit";
	private static List<String> mainMenuOptions = new ArrayList<String>();
	private static final String VIEW_CAMPGROUNDS = "Make Reservation for Campground";
	private static final String SEARCH_FOR_RSRV = "Search for Parkwide Reservation";
	private static final String ADVANCED_SEARCH = "Search based on advanced criteria";
	private static final String RETURN_SCREEN = "Return to Previous Screen";
	private static final String SEE_RESERVATIONS = "See upcoming reservations in the next 30 days";
	private static final String[] VACATION_MENU_OPTIONS = {VIEW_CAMPGROUNDS,
			SEARCH_FOR_RSRV,SEE_RESERVATIONS,RETURN_SCREEN};
	private static final String SEARCH_FOR_AVAI_RSRV = "Search for Available Reservations";
	private static final String[] AVAILABLE_MENU_OPTIONS = {SEARCH_FOR_AVAI_RSRV,ADVANCED_SEARCH, RETURN_SCREEN};
	private static JDBCParkDAO parkDao;
	private static JDBCCampgroundDAO campgroundDao;
	private static JDBCReservationDAO reservationDao;
	private static JDBCSiteDAO siteDao;
	
	private static Menu campgroundMenu;

	public CampgroundCLI(DataSource dataSource) {
		
		campgroundMenu = new Menu(System.in, System.out);
		parkDao = new JDBCParkDAO(dataSource);
		campgroundDao = new JDBCCampgroundDAO(dataSource);
		reservationDao = new JDBCReservationDAO(dataSource);
		siteDao = new JDBCSiteDAO(dataSource);
		
		List<Park> allParks = parkDao.getAllParks();
		for (int i = 0; i < allParks.size(); i++) {
			String aParkName = allParks.get(i).getName();
			mainMenuOptions.add(aParkName);
			
		}
		mainMenuOptions.add(QUIT_MENU);
		
	}

	public void run() {
		System.out.println(BREAK_LINE);
		System.out.println(MAIN_MENU_GREETING);
		System.out.print(BREAK_LINE);
		
		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			String[] allOptions = new String[mainMenuOptions.size()];
			for (int i = 0; i < mainMenuOptions.size(); i++) {
				allOptions[i] = mainMenuOptions.get(i);
			}
			
			//Problem child below
			//I don't remember why I wrote the above comment, but I do remember it being important
			//Clark remembers and says its not important
			String choice = (String)campgroundMenu.getChoiceFromOptions(allOptions);  // Display menu and get choice
			if (choice == QUIT_MENU) {
				shouldProcess = false;
				System.out.println("Yeah go away you hooligan!");
			} else {
				
				Park chosenPark = parkDao.searchByParkName(choice);
				displayParkInfo(chosenPark);
				
			}
			
		}
		return;                  
	}
	//f( "%-15s %15s %n", heading1, heading2);
	public void displayParkInfo(Park aPark) {
		System.out.println(BREAK_LINE);
		System.out.println("");
		System.out.println("****** "+aPark.getName() + " National Park ******");
		System.out.println("");
		System.out.printf("%-19s %-30s %n","Location: ", aPark.getLocation());
		System.out.printf("%-19s %-30s %n","Established: " , aPark.getEstablish_date());
		System.out.printf("%-19s %-15s %n","Area: " , displayIntWithCommas(aPark.getArea()) + " sq-km");
		System.out.printf("%-19s %-15s %n","Annual Visitors: " , displayIntWithCommas(aPark.getVisitors()));
		String s = "\n" + aPark.getDescription();
		StringBuilder sb = new StringBuilder(s);

		int i = 0;
		while (i + 20 < sb.length() && (i = sb.lastIndexOf(" ", i + 50)) != -1) {
		    sb.replace(i, i + 1, "\n");
		}

		System.out.println(sb.toString());
		System.out.println("");
		displayVacationMenu(aPark);
	}
	
	public String displayIntWithCommas(int num) {
		String withCommas = "";
		while (num >= 1000) {
			int temp = num;
			num = num/1000; //rounds off last three digits;
			temp = temp - num * 1000;
			withCommas = "," + temp + withCommas;
		}
		withCommas = num + withCommas;
		return withCommas;
		
	}
	
	public void displayVacationMenu(Park aPark) {
		System.out.println(BREAK_LINE);
		System.out.println("Select a command                  |");
		System.out.println(BREAK_LINE);
		
		
		boolean shouldProcess = true;         // Loop control variable
		
		while(shouldProcess) {                // Loop until user indicates they want to exit
			
			
			String choice = (String)campgroundMenu.getChoiceFromOptions(VACATION_MENU_OPTIONS);  // Display menu and get choice
			
			switch(choice) {
				case VIEW_CAMPGROUNDS:
					displayAllCampgroundsInPark(aPark);
					break;
				case SEARCH_FOR_RSRV:
					searchForRsrv(aPark);
					break;
				case SEE_RESERVATIONS:
					seeReservations(aPark);
					break;
				case RETURN_SCREEN:
					shouldProcess = false;
					break;
			}

			
		}
	}
	
	private void seeReservations(Park aPark) {
		System.out.println(BREAK_LINE);
		LocalDate today = LocalDate.now();
		List<Reservation> upcomingReservations = reservationDao.searchByDateAndPark(aPark,today,today.plusDays(30));
		
		System.out.printf("%8s %9s %18s %24s %13s %15s  %n","Conf Num","Site ID",
							"Name","Start Date","End Date","Created On");
		System.out.println("");
		//print out those reservations
		for (int i = 0; i < upcomingReservations.size(); i++) {
			Reservation aReservation = upcomingReservations.get(i);
			System.out.printf("%-11s",aReservation.getReservation_id());
			System.out.printf("%-11s",aReservation.getSite_id());
			System.out.printf("%-30s",aReservation.getName());
			System.out.printf("%-15s",aReservation.getFrom_date());
			System.out.printf("%-15s",aReservation.getTo_date());
			System.out.printf("%-20s %n",aReservation.getCreate_date());
		}
	}

	public void displayAllCampgroundsInPark(Park aPark) {
		System.out.println(BREAK_LINE);
		System.out.println("");
		int aParkId = aPark.getPark_id();
		List<Campground> campgrounds = campgroundDao.searchByParkId(aParkId);
		
		System.out.printf("%11s %21s %17s %18s %n","Name","Open","Close","Daily Fee");
		System.out.printf("%11s %21s %17s %18s %n","----","----","-----","---------");
		
		for (int i = 0; i < campgrounds.size(); i++) {
			Campground aCampground = campgrounds.get(i);  //only noobs use for each loops
			System.out.print("#" + (i+1) + " ");
			System.out.printf("%-25s",aCampground.getName());
			System.out.printf("%-17s",convertIntToMonth(Integer.parseInt(aCampground.getOpen_from_mm())));
			System.out.printf("%-18s",convertIntToMonth(Integer.parseInt(aCampground.getOpen_to_mm())));
			BigDecimal moneyMoney = aCampground.getDaily_fee();
			NumberFormat.getCurrencyInstance().format(moneyMoney);
			System.out.printf("%6s %n",moneyMoney);
			
		}
		System.out.println(" ");
		System.out.println(" ");
		Scanner input = new Scanner(System.in);
		boolean finished = false;
		int userChoice = 0;
		while(!finished) {
			
			System.out.print("Please enter your desired campground: ");
			System.out.println(" ");
			String aLine = input.nextLine();
			try {
				userChoice = Integer.parseInt(aLine);
			} catch (NumberFormatException e) {
				System.out.println("Panic and emptiness! Insert whole numbers only!");
				continue;
			}
			if (userChoice < 1||userChoice > campgrounds.size()) {
				System.out.println("What??? That isn't even a real campground!!!");
				System.out.println("For pete's sake pick from one of the campgrounds we showed you!!!");
			}
			finished = true;
		}
		
		displayAvailableMenu(campgrounds.get(userChoice-1));
	}
	
	public void displayAvailableMenu(Campground aCampground) {
		System.out.println(BREAK_LINE);
		System.out.println("Select a Command                  |");
		System.out.println(BREAK_LINE);
		
		String choice = (String)campgroundMenu.getChoiceFromOptions(AVAILABLE_MENU_OPTIONS);
		
		switch (choice) {
		case SEARCH_FOR_AVAI_RSRV:
			searchForRsrvByCampground(aCampground,false);
			break;
		case ADVANCED_SEARCH:
			searchForRsrvByCampground(aCampground,true);
			break;
		case RETURN_SCREEN:
			
			break;
		}
	}
	
	public void searchForRsrv(Park aPark) {
		System.out.println(BREAK_LINE);
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		boolean donePicking = false;
		Site chosenSite = new Site();
		while (!donePicking)
		{
		boolean finished = false;
		while (!finished) {
			System.out.println(" ");
			startDate = getDateFromUser("What is the start date? yyyy/mm/dd  >>>");
			System.out.println(" ");
			if (startDate.isBefore(LocalDate.now())) {
				System.out.println("That would be a great idea if you were a time traveller!");
				System.out.println("Maybe pick a date that's not before today!");
				System.out.println(" ");
				continue;
			}
			finished = true;
		}
		finished = false;
		while (!finished) {
			endDate = getDateFromUser("What is the end date? yyyy/mm/dd >>>");
			System.out.println(" ");
			
			if (startDate.compareTo(endDate) > 0) {
				System.out.println("That vacation is so short it doesn't exist!");
				System.out.println("Intelligent people typically put the start date before the end date.");
				System.out.println(" ");
				continue;
			}
			finished = true;
		}
		
		List<Campground> campgrounds = campgroundDao.searchByParkId(aPark.getPark_id());
		List<Site> availableSites = new ArrayList<Site>();
		for (int i = 0; i < campgrounds.size(); i++) {
			List<Site> temp = reservationDao.searchByDateAndCampground(startDate,endDate,campgrounds.get(i));
			boolean isAvailable = campgroundDao.isOpenOnDates(startDate, endDate,campgrounds.get(i));
			if (isAvailable) {
				availableSites.addAll(temp);
			}
		}
		
		if (availableSites.size() > 0) {
			for (int i = 5; i < availableSites.size(); ) {
				availableSites.remove(i);
			}
			System.out.println("");
			System.out.println("Success! We can indeed make a reservation on that day!!!");
			System.out.println("");
			System.out.println("--------------Here are the available sites--------------");
			
			System.out.println("");
			System.out.printf("%6s %15s %15s %15s %15s %15s %n","Site no.", "Max Occup.", 
								"Accessible??", "Max RV Length", "Utility", "Cost");
			System.out.printf("%6s %15s %15s %15s %15s %15s %n","--------", "----------", 
					"------------", "-------------", "-------", "----");
			for (int i = 0; i < availableSites.size(); i++) {
				
				Site aSite = availableSites.get(i);
				
				System.out.printf("%4s",aSite.getSite_id());
				System.out.printf("%17s",aSite.getMax_occupancy());
				System.out.printf("%15s",convertBoolToMessage(aSite.getAccessible()));
				System.out.printf("%14s",aSite.getMax_rv_length());
				System.out.printf("%19s",convertBoolToMessage(aSite.isUtilities()));
				BigDecimal totalCost = campgroundDao.searchByCampgroundId(aSite.getCampground_id()).getDaily_fee();
				long numOfDays = ChronoUnit.DAYS.between(endDate,startDate);
				totalCost = totalCost.multiply(new BigDecimal(Long.toString(numOfDays)));
				System.out.printf("%22s %n",NumberFormat.getCurrencyInstance().format(totalCost));
			}
				donePicking = true;
				chosenSite = getSiteChoiceFromUser(availableSites); //arguments? Parameters
			} else {
			System.out.println("Ha Ha, no sites available!");
			//	System.out.println("(Or just isn't open... pretty stoopid to close parks IMO)");
				System.out.println("Pick another date stupid!");
				System.out.println(" ");
			}
		}
		createNewReservation(startDate,endDate,chosenSite);
	}
	// This controls the route for normal reservations without it being specific. 
	public void searchForRsrvByCampground(Campground aCampground,boolean advancedSearch) {
		System.out.println(BREAK_LINE);
		System.out.println("");
		boolean donePicking = false;
		Site chosenSite = new Site();
		LocalDate startDate = LocalDate.now();
		LocalDate endDate = LocalDate.now();
		while (!donePicking)
		{
			startDate = LocalDate.now();
			endDate = LocalDate.now();
			boolean finished = false;
			while (!finished) {
				startDate = getDateFromUser("What is the start date? yyyy/mm/dd >>>");
				System.out.println("");
				if (startDate.isBefore(LocalDate.now())) {
					System.out.println("That would be a great idea if you were a time traveller!");
					System.out.println("Maybe pick a date that's not before today?");
					continue;
				}
				finished = true;
			}
			finished = false;
			while (!finished) {
				endDate = getDateFromUser("What is the end date? yyyy/mm/dd >>>");
			
				if (startDate.compareTo(endDate) > 0) {
					System.out.println("That vacation is so short it doesn't exist!");
					System.out.println("Intelligent people typically put the start date before the end date.");
					continue;
				}
				finished = true;
			}
		
			boolean isAvailable = campgroundDao.isOpenOnDates(startDate, endDate, aCampground);
			List<Site> availableSites = reservationDao.searchByDateAndCampground(startDate,endDate,aCampground);
			
			if (advancedSearch) {
				availableSites = advancedSearch(availableSites);
			}

			
			
			if (isAvailable&&availableSites.size() > 0) {
				for (int i = 5; i < availableSites.size(); ) {
					availableSites.remove(i);
				}
				System.out.println("");
				System.out.println("Success! We can indeed make a reservation on that day!!!");
				System.out.println("");
				System.out.println("--------------Here are the available sites--------------");
				
				System.out.println("");
				System.out.printf("%6s %15s %15s %15s %15s %15s %n","Site no.", "Max Occup.", 
									"Accessible??", "Max RV Length", "Utility", "Cost");
				System.out.printf("%6s %15s %15s %15s %15s %15s %n","--------", "----------", 
						"------------", "-------------", "-------", "----");
				BigDecimal totalCost = aCampground.getDaily_fee();
				long numOfDays = ChronoUnit.DAYS.between(endDate,startDate);
				totalCost = totalCost.multiply(new BigDecimal(Long.toString(numOfDays)));
				for (int i = 0; i < availableSites.size(); i++) {
					
					Site aSite = availableSites.get(i);
					
					System.out.printf("%4s",aSite.getSite_id());
					System.out.printf("%17s",aSite.getMax_occupancy());
					System.out.printf("%15s",convertBoolToMessage(aSite.getAccessible()));
					System.out.printf("%14s",aSite.getMax_rv_length());
					System.out.printf("%19s",convertBoolToMessage(aSite.isUtilities()));
					System.out.printf("%22s %n",NumberFormat.getCurrencyInstance().format(totalCost));
				}
				donePicking = true;
				chosenSite = getSiteChoiceFromUser(availableSites); //arguments? Parameters
			} else {
				System.out.println("Ha Ha, no sites available!");
			//	System.out.println("(Or just isn't open... pretty stoopid to close parks IMO)");
				System.out.println("Pick another date stupid!");
			}
		}
		createNewReservation(startDate,endDate,chosenSite);
		
	}
	
	public List<Site> advancedSearch (List<Site> sites){
		System.out.println(BREAK_LINE);
		System.out.println("");
		boolean finished = false;
		Scanner input = new Scanner(System.in);
		String aLine = ("");
		boolean utilities = true;
		int maxOcc = 0;
		int maxRv = 0;
		boolean rvs = true;
		while (!finished) {
			System.out.println("How many people do you expect in your reservation? (max is 55) >>>");
			
			aLine = input.nextLine();
			try {maxOcc = Integer.parseInt(aLine);
				finished = true;
			}catch (NumberFormatException wrong) {
			System.out.println("Wrong!");
			System.out.println("");
			}	
			System.out.println("");
		} 
			finished = false;
		while (!finished) {
			System.out.println("Do you need a handicap accessible site? (Y/N) >>>");
			
			aLine = input.nextLine();
			aLine = aLine.toUpperCase();
			if (aLine.equals("Y")) {
				rvs = true;
				finished = true;
			}
			if (aLine.equals("N")) {
				rvs = false;
				finished = true;
			}
			if (! finished) {
				System.out.println("That wasn't one of the answers!!");
			}
			System.out.println("");
		}
		finished = false;
		while (!finished) {
			System.out.println("Do you need utilities? (Y/N) >>>");
			
			aLine = input.nextLine();
			aLine = aLine.toUpperCase();
			if (aLine.equals("Y")) {
				utilities = true;
				finished = true;
			}
			if (aLine.equals("N")) {
				utilities = false;
				finished = true;
			}
			if (! finished) {
				System.out.println("That wasn't one of the answers!!");
			}
			System.out.println("");
		} 
		finished = false;
		while (!finished) {
			System.out.println("What is the length of your R/V? (upto 35 or 0 if no R/V) >>>");
		
			aLine = input.nextLine();
			try {maxRv = Integer.parseInt(aLine);
				finished = true;
			} catch (NumberFormatException wrong) {
				System.out.println("Wrong!");
				System.out.println("");
			}
			System.out.println("");
		}
		sites = siteDao.siteAccomadations(sites, maxOcc, rvs, maxRv, utilities);
		return sites;
		
	}
	
	public LocalDate getDateFromUser(String userPrompt) {
		boolean finished = false;
		int year = 0;
		int month = 0;
		int day = 0;
		while (!finished) {
			System.out.println(userPrompt);
			Scanner input = new Scanner(System.in);
			String aLine = input.nextLine();
			String[] lines = aLine.split("/");
			if (lines.length != 3) {
				System.out.println("This isn't complicated... you need three numbers separated by slashes... nøøb.");
				System.out.println("");
				continue;
				
			}
			if (lines[0].length() != 4) {
				System.out.println("You think that's a real year? What a moron you are!");
				System.out.println("");
				continue;
			}
			try {
				year = Integer.parseInt(lines[0]);
			} catch (NumberFormatException e) {
				System.out.println("That year isn't even a whole number! You can't be that stupid!");
				System.out.println("");
				continue;
			}
			if (lines[1].length() < 1||lines[1].length() > 2) {
				System.out.println("You think that's a real month? What a moron you are!");
				System.out.println("");
				continue;
			}
			try {
				month = Integer.parseInt(lines[1]);
			} catch (NumberFormatException e) {
				System.out.println("That month isn't even a whole number! You can't be that stupid!");
				System.out.println("");
				continue;
			}
			if (month > 12||month < 1) {
				System.out.println("You think that's a real month? What a moron you are!");
				System.out.println("");
				continue;
			}
			if (lines[2].length() < 1||lines[2].length() > 2) {
				System.out.println("You think that's a real day? What a moron you are!");
				System.out.println("");
				continue;
			}
			try {
				day = Integer.parseInt(lines[2]);
			} catch (NumberFormatException e) {
				System.out.println("That day isn't even a whole number! You can't be that stupid!");
				System.out.println("");
				continue;
			}
			if (day < 0||day > 31) {
				System.out.println("You think that's a real day? What a moron you are!");
				System.out.println("");
				continue;
			}
			finished = true;
			
		}
		LocalDate userDate = LocalDate.of(year,month,day);
		return userDate;
	}
	
	public Site getSiteChoiceFromUser(List<Site> availableSites) {
		System.out.println(" ");
		Site result = new Site();
		boolean finished = false;
		Scanner input = new Scanner(System.in);
		int userChoice = 0;
		while (!finished) {
			System.out.println("");
			System.out.print("Please pick a site from the ones selected: ");
			System.out.println("");
			String aLine = input.nextLine();
			try {
				userChoice = Integer.parseInt(aLine);
				for (int i = 0; i < availableSites.size(); i++) {
					if (userChoice == availableSites.get(i).getSite_id()) {
						finished = true;
						result = availableSites.get(i);
					}
				}
				if (!finished) {
					System.out.println("Whoa-hoa-ho! That isn't one of the available sites!");
					System.out.println("");
				}
			} catch (NumberFormatException clark) {
				System.out.println("That isn't a number!!");
				System.out.println("");
			}
			
		}
		return result;
	}
	
	public void createNewReservation(LocalDate startDate,LocalDate endDate,Site chosenSite) {
		System.out.println(" ");
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter a name for the reservation: >>>");
		System.out.println("");
		String name = input.nextLine();
		Reservation aReservation = new Reservation();
		aReservation.setFrom_date(startDate);
		aReservation.setTo_date(endDate);
		aReservation.setName(name);
		aReservation.setCreate_date(LocalDate.now());
		aReservation.setSite_id(chosenSite.getSite_id());
		//aReservation.setReservation_id(100000);
		int confirmationId = reservationDao.save(aReservation);
		System.out.println();
		System.out.println("Your confirmation id is " + confirmationId);
		System.out.println("");
	}
	
	
	public String convertIntToMonth(int month) {
		switch (month) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		default:
			return "##Invalid Month##";
		}
	}
	
	public String convertBoolToMessage(boolean isBoolean) {
		try {
			if (isBoolean) {
				return "Yes";
			}
			return "No";
		} catch (NullPointerException e) {
			return "N/A";
		}
	}
}
