package com.techelevator.campground;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public interface CampgroundDAO {
	
public void save(Campground theCampground);   
//public void save(String theName); 

//public void delete(Campground theCampground);                 
//public void delete(String theName);                   
//public void delete(String theName, int theParkId);     

//public void update(Campground theCampground);                  
//public void update(String theName);  

public Campground  searchByCampgroundId(int theParkId);  
public List <Campground>  searchByParkId(int theParkId); 
public List <Campground>  searchByDatesOpen (LocalDate campDateOpen, LocalDate campDateClose);

public List <Campground>  getAllCampgrounds();                       


}
//campground_id
//park_id
//name
//open_from_mm
//open_to_mm
//daily_fee