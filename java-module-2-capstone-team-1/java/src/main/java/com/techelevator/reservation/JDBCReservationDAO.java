package com.techelevator.reservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.Campground;
import com.techelevator.campground.JDBCCampgroundDAO;
import com.techelevator.park.Park;
import com.techelevator.site.JDBCSiteDAO;
import com.techelevator.site.Site;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	private JDBCCampgroundDAO jdbcCampgroundDao;
	private JDBCSiteDAO jdbcSiteDao;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcCampgroundDao = new JDBCCampgroundDAO(dataSource);
		jdbcSiteDao = new JDBCSiteDAO(dataSource);
	}
	@Override
	public int save(Reservation theReservation) {
		int confirmationId = 0;
		String sqlSaveReservation = "Insert into reservation (site_id, name, from_date, to_date, create_date) " + 
				"values (?,?,?,?,?)";
		
		jdbcTemplate.update(sqlSaveReservation,theReservation.getSite_id() 
                ,theReservation.getName()
                ,theReservation.getFrom_date()
               ,theReservation.getTo_date()
				,theReservation.getCreate_date()) ;	
			String sqlGetNextId = "select last_value from reservation_reservation_id_seq;";  //I don't know why this works but we tested it in dbVisualizer and it does
			SqlRowSet rowReturned = jdbcTemplate.queryForRowSet(sqlGetNextId);
		if (rowReturned.next()) {
			//System.out.println("I did it!");
			confirmationId = rowReturned.getInt(1);
		}
	
		return confirmationId;
	}

//	@Override
//	public void save(String theReservationName) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void delete(Reservation theReservation) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void delete(String theReservationName) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void delete(int theReservationName) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void update(Reservation theReservation) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void update(String theReservationName) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Reservation searchByReservationID(int theReservationId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Reservation searchByReservationName(String theReservationName) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Site> searchForOpenDate(LocalDate startDate, LocalDate endDate) {
		//List<Reservation> results = new ArrayList<Reservation>();
		List<Campground> openCampgrounds = jdbcCampgroundDao.searchByDatesOpen(startDate, endDate);
		List<Site> openSites = new ArrayList<Site>();
		for (int i = 0; i < openCampgrounds.size(); i++) {
			int campgroundId = openCampgrounds.get(i).getCampground_id();
			List<Site> sites = jdbcSiteDao.searchByCampgroundId(campgroundId);
			openSites.addAll(sites);
		}
		int startYear = startDate.getYear();
		int endYear = endDate.getYear();
		int startMonth = startDate.getMonthValue();
		int endMonth = endDate.getMonthValue();
		for (int i = 0; i < openSites.size(); i++) {
			List<Reservation> reservations = searchBySiteId(openSites.get(i).getSite_id());
			for (int z = 0; z < reservations.size(); z++) {
				Reservation aReservation = reservations.get(z);
				int rsrvStartMonth = aReservation.getFrom_date().getMonthValue();
				int rsrvEndMonth = aReservation.getTo_date().getMonthValue();
				int rsrvStartYear = aReservation.getFrom_date().getYear();
				int rsrvEndYear = aReservation.getTo_date().getYear();
			
				if (startYear >= rsrvStartYear
					&&endYear <= rsrvEndYear
					&&startMonth >= rsrvStartMonth
					&&endMonth <= rsrvEndMonth) {
					openSites.remove(i);
					i--;
				}
			}
		}
		return openSites;
	}
	
	public List<Reservation> searchByDateAndPark(Park aPark,LocalDate startDate, LocalDate endDate) {  //searches for all reservations in that time period
		List<Reservation> results = new ArrayList<Reservation>();
		int aParkId = aPark.getPark_id();
		List<Campground> campgrounds = jdbcCampgroundDao.searchByParkId(aParkId);
		for (int i = 0; i < campgrounds.size(); i++) {
			int aCampgroundId = campgrounds.get(i).getCampground_id();
			List<Site> sites = jdbcSiteDao.searchByCampgroundId(aCampgroundId);
			for (int z = 0; z < sites.size(); z++) {
				int aSiteId = sites.get(z).getSite_id();
				List<Reservation> reservations = searchBySiteId(aSiteId);
				results.addAll(reservations);
			}
		}
		
		System.out.println(results.size());
		for (int i = 0; i < results.size(); i++) {
			Reservation aRsrv = results.get(i);
			LocalDate rsrvStartDate = aRsrv.getFrom_date();
			LocalDate rsrvEndDate = aRsrv.getTo_date();
			if((endDate.compareTo(rsrvStartDate) < 0)
					||(startDate.compareTo(rsrvEndDate) > 0)) {
				results.remove(i);
				i--;
			}
		}
		
		return results;
	}
	
	public List<Site> searchByDateAndCampground(LocalDate fromDate, LocalDate toDate,Campground aCampground) {
		//List<Reservation> results = new ArrayList<Reservation>();
		List<Site> openSites = new ArrayList<Site>();
		int campgroundId = aCampground.getCampground_id();
		openSites = jdbcSiteDao.searchByCampgroundId(campgroundId);
//		int startYear = fromDate.getYear();
//		int endYear = toDate.getYear();
//		int startMonth = fromDate.getMonthValue();
//		int endMonth = toDate.getMonthValue();
		for (int i = 0; i < openSites.size(); i++) {
			List<Reservation> reservations = searchBySiteId(openSites.get(i).getSite_id());
			for (int z = 0; z < reservations.size(); z++) {
				Reservation aReservation = reservations.get(z);
				LocalDate rsrvStartDate = aReservation.getFrom_date();
				LocalDate rsrvEndDate = aReservation.getTo_date();
//				int rsrvStartMonth = aReservation.getFrom_date().getMonth();
//				int rsrvEndMonth = aReservation.getTo_date().getMonth();
//				int rsrvStartYear = aReservation.getFrom_date().getYear();
//				int rsrvEndYear = aReservation.getTo_date().getYear();
//			
//				if (startYear >= rsrvStartYear
//					&&endYear <= rsrvEndYear
//					&&startMonth >= rsrvStartMonth
//					&&endMonth <= rsrvEndMonth) {
				
				if((fromDate.compareTo(rsrvStartDate) >= 0&&toDate.compareTo(rsrvEndDate) <= 0)
						||(fromDate.compareTo(rsrvStartDate) <= 0&&toDate.compareTo(rsrvStartDate) >= 0)
							||(fromDate.compareTo(rsrvEndDate) <= 0&&toDate.compareTo(rsrvEndDate) >= 0)) {
					openSites.remove(i);
					i--;
				}
			}
		}
		return openSites;
	}

	@Override
	public List<Reservation> getAllReservation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Reservation> searchBySiteId(int siteId) {
		List<Reservation> results = new ArrayList<Reservation>();
		String sqlSearchBySiteId = "select * from reservation where site_id = ?;";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlSearchBySiteId, siteId);
		while (rowsReturned.next()) {
			Reservation theReservation = mapRowToReservation(rowsReturned);
			results.add(theReservation);
		}
		return results;
	}
		private Reservation mapRowToReservation(SqlRowSet rowsReturned) {
			Reservation aReservation = new Reservation();
			aReservation.setSite_id(rowsReturned.getInt("site_id"));
			aReservation.setName(rowsReturned.getString("name"));
			aReservation.setFrom_date(rowsReturned.getDate("from_date").toLocalDate());
			aReservation.setTo_date(rowsReturned.getDate("to_date").toLocalDate());
			aReservation.setCreate_date(rowsReturned.getDate("create_date").toLocalDate());
		
			
			return aReservation;

}
	}
