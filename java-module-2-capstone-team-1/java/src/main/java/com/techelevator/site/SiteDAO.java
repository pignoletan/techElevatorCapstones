package com.techelevator.site;

import java.sql.Date;
import java.util.List;

public interface SiteDAO {
	

//	public void save(Site theSite);   
//	public void save(String theSiteName); 
//
//	public void delete(Site theSite);                 
//	public void delete(String theSiteName);                   
//	public void delete(int theSitenName);     
//
//	public void update(Site theSite);                  
//	public void update(String theSiteName);  

	
	public Site  searchByDate(Date fromDate, Date toDate);
	public Site  searchBySiteId(int theSiteId); 
	public Site  searchBySiteName(String theSiteName); 
	public List<Site>  siteAccomadations(List <Site> sites, 
										int max_occupancy, 
										boolean accessible, 
										int max_rv_length, 
										boolean utilities); 
	
	public List<Site>  searchByCampgroundId(int theCampgroundId); 
	public List<Site>  getAllSite(); 

}
//site_id
//campground_id
//site_number
//max_occupancy
//accessible
//max_rv_length
//utilities