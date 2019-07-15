package com.techelevator;

import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.park.JDBCParkDAO;
import com.techelevator.park.Park;
import com.techelevator.view.Menu;

public class CampgroundApp {

	private static Menu campgroundMenu;
		
		
		
	public static void main(String[] args) {
		campgroundMenu = new Menu(System.in, System.out);
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

}
