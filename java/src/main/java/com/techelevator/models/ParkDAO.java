package com.techelevator.models;

import java.util.List;

public interface ParkDAO {
	
	public Park getParkByCode(String parkCode);
	
	
	public List<Park> getAllParks();
}
