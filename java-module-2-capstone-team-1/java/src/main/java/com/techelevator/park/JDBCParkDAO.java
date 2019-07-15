package com.techelevator.park;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {

	JdbcTemplate jdbcTemplate;
	
	public JDBCParkDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
//	@Override
//	public void save(Park thePark) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void save(String parkName) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delete(Park thePark) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delete(String parkName) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void delete(int theParkId) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void update(Park thePark) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void update(String parkName) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Park searchByParkLocation(String theParkLocation) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Park searchByParkId(int theParkId) {
		Park aPark = new Park();
		String sqlSearchParkId = "select * from park where park_id = ?;";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlSearchParkId, theParkId);
		if (rowsReturned.next()) {
			aPark = mapRowToPark(rowsReturned);
		}
		return aPark;
	}

	@Override
	public Park searchByParkName(String parkName) {
		Park aPark = new Park();
		String sqlSearchName = "select * from park where name = ?;";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlSearchName,parkName);
		if (rowsReturned.next()) {
			aPark = mapRowToPark(rowsReturned);
		}
		return aPark;
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> allParks = new ArrayList<Park>();
		String sqlSearchAll = "select * from park;";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlSearchAll);
		while (rowsReturned.next()) {
			Park aPark = mapRowToPark(rowsReturned);
			allParks.add(aPark);
		}
		return allParks;
	}
	
	private Park mapRowToPark(SqlRowSet rowsReturned) {
		Park aPark = new Park();
		aPark.setPark_id(rowsReturned.getInt("park_id"));
		aPark.setName(rowsReturned.getString("name"));
		aPark.setArea(rowsReturned.getInt("area"));
		aPark.setDescription(rowsReturned.getString("description"));
		aPark.setEstablish_date(rowsReturned.getDate("establish_date"));
		aPark.setLocation(rowsReturned.getString("location"));
		aPark.setVisitors(rowsReturned.getInt("visitors"));
		return aPark;
	}

}
