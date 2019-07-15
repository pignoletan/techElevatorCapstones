package com.techelevator.campground;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public void save(Campground theCampground) {
		// TODO Auto-generated method stub
		String sqlInsertCampground = "Insert into campground (park_id,name,open_from_mm,open_to_mm,daily_fee) " + 
				"values (?,?,?,?,?)";
		jdbcTemplate.update(sqlInsertCampground,theCampground.getPark_id() // create primary key using new data
                ,theCampground.getName()
                ,theCampground.getOpen_from_mm()
                ,theCampground.getOpen_to_mm()
                ,theCampground.getDaily_fee());	
	}

//	@Override
//	public void save(String theName) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void delete(Campground theCampground) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delete(String theName) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delete(String theName, int theParkId) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void update(Campground theCampground) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void update(String theName) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public Campground searchByCampgroundId(int theCampgroundId) {
	String sqlSearchCampgroundId = "select * from campground where campground_id = ?;";
	SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlSearchCampgroundId, theCampgroundId);
	Campground results = new Campground();
	while (rowsReturned.next()) {
		Campground aCampground = mapRowToCampground(rowsReturned);
		results = aCampground;
	}
		return results;
	}

	@Override
	public List<Campground> searchByParkId(int theParkId) {
		List<Campground> results = new ArrayList<Campground>();
		String sqlSearchParkId = "select * from campground where park_id = ?;";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlSearchParkId, theParkId);
		while (rowsReturned.next()) {
			Campground aCampground = mapRowToCampground(rowsReturned);
			results.add(aCampground);
		}
		
		return results;
	}


	@SuppressWarnings("deprecation")
	@Override
	public List<Campground> searchByDatesOpen(LocalDate campDateOpen, LocalDate campDateClose) {
		List<Campground> allCampgrounds = getAllCampgrounds();
		List<Campground> allDates = new ArrayList<Campground>();
		for (int i = 0; i < allCampgrounds.size(); i++) {
			if (isOpenOnDates(campDateOpen,campDateClose,allCampgrounds.get(i))) {
				allDates.add(allCampgrounds.get(i));
			}
		}
		return allDates;
	}
	
	public boolean isOpenOnDates(LocalDate startDate, LocalDate endDate, Campground aCampground) {
		int openMonth = Integer.parseInt(aCampground.getOpen_from_mm());
		int closeMonth = Integer.parseInt(aCampground.getOpen_to_mm());
		if (openMonth == 1&&closeMonth == 12) {  //If the park is open year round
			return true;
		}
		int startMonth = startDate.getMonthValue();
		int endMonth = endDate.getMonthValue();
		int startYear = startDate.getYear();
		int endYear = endDate.getYear();
		if (startYear == endYear&&startMonth >= openMonth&&endMonth <= closeMonth) {
			return true;
		}
		
		return false;
	}


	@Override
	public List<Campground> getAllCampgrounds() {
		List<Campground> results = new ArrayList<Campground>();
		String sqlgetAllCampgrounds = "select * from campground Order by name;";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlgetAllCampgrounds);
		while (rowsReturned.next()) {
			Campground aCampground = mapRowToCampground(rowsReturned);
			results.add(aCampground);
		}
		return results;
	}
	
	private Campground mapRowToCampground(SqlRowSet rowsReturned) {
		Campground aCampground = new Campground();
		
		aCampground.setCampground_id(rowsReturned.getInt("campground_id"));
		aCampground.setName(rowsReturned.getString("name"));
		aCampground.setPark_id(rowsReturned.getInt("park_id"));
		aCampground.setOpen_from_mm(rowsReturned.getString("open_from_mm"));
		aCampground.setOpen_to_mm(rowsReturned.getString("open_to_mm"));
		aCampground.setDaily_fee(rowsReturned.getBigDecimal("daily_fee"));
		
		return aCampground;
	}

}
