package com.techelevator.park;

import java.util.List;

public interface ParkDAO {
		
//		public void save(Park thePark);   
//		public void save(String parkName); 
//
//		public void delete(Park thePark);                 
//		public void delete(String parkName);                   
//		public void delete(int theParkId);     
//
//		public void update(Park thePark);                  
//		public void update(String parkName);  
//
//		public Park searchByParkLocation(String theParkLocation);  
		public Park searchByParkId(int theParkId); 
		public Park searchByParkName(String parkName);  
		
		public List<Park>  getAllParks();  
		
}
