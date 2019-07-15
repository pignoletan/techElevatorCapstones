package com.techelevator.reservation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.techelevator.site.Site;

public interface ReservationDAO {

	public int save(Reservation theReservation);   
//	public void save(String theReservationName); 

//	public void delete(Reservation theReservation);                 
//	public void delete(String theReservationName);                   
//	public void delete(int theReservationName);     

//	public void update(Reservation theReservation);                  
//	public void update(String theReservationName);  
//
//	public Reservation  searchByReservationID(int theReservationId); 
//	public Reservation  searchByReservationName(String theReservationName);
	public List<Site>  searchForOpenDate(LocalDate fromDate, LocalDate toDate);
	public List<Reservation>  searchBySiteId(int siteId);

	public List<Reservation>  getAllReservation();  
	
}
//reservation_id
//site_id
//name
//from_date
//to_date
//create_date