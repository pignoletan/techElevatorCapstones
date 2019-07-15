package com.techelevator.models;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcParkDAO implements ParkDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Park getParkByCode(String parkCode) {
		Park aPark = new Park();
		String sqlSelectPark = "select * from park where parkcode = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectPark,parkCode);
		if (results.next()) {
			aPark = mapRowToPark(results);
		}
		return aPark;
	}
	
	public Park mapRowToPark(SqlRowSet rowReturned) {
		Park aPark = new Park();
		aPark.setParkCode(rowReturned.getString("parkcode"));
		aPark.setParkName(rowReturned.getString("parkname"));
		aPark.setState(rowReturned.getString("state"));
		aPark.setAcreage(rowReturned.getLong("acreage"));
		aPark.setElevationInFeet(rowReturned.getLong("elevationinfeet"));
		aPark.setMilesOfTrail(rowReturned.getDouble("milesoftrail"));
		aPark.setNumOfCampsites(rowReturned.getLong("numberofcampsites"));
		aPark.setClimate(rowReturned.getString("climate"));
		aPark.setYearFounded(rowReturned.getLong("yearfounded"));
		aPark.setAnnualVisitorCount(rowReturned.getLong("annualvisitorcount"));
		aPark.setInspirationalQuote(rowReturned.getString("inspirationalquote"));
		aPark.setQuoteSource(rowReturned.getString("inspirationalquotesource"));
		aPark.setParkDescription(rowReturned.getString("parkdescription"));
		aPark.setEntryFee(rowReturned.getLong("entryfee"));
		aPark.setNumOfAnimalSpecies(rowReturned.getLong("numberofanimalspecies"));
		return aPark;
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> allParks = new ArrayList<>();
		String sqlSelectPark = "select * from park order by parkname";
		SqlRowSet rowReturned = jdbcTemplate.queryForRowSet(sqlSelectPark);
		while(rowReturned.next()) {
			allParks.add(mapRowToPark(rowReturned));
		}
		return allParks;
	}

}
