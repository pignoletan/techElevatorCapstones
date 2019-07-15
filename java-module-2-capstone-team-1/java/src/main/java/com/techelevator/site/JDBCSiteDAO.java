package com.techelevator.site;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.JDBCCampgroundDAO;



public class JDBCSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Site searchByDate(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Site searchBySiteId(int theSiteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Site searchBySiteName(String theSiteName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Site> searchByCampgroundId(int theCampgroundId) {
		List<Site> results = new ArrayList<Site>();
		String sqlSearchCampgroundId = "select * from site where campground_id = ?;";
		SqlRowSet rowsReturned = jdbcTemplate.queryForRowSet(sqlSearchCampgroundId,theCampgroundId);
		while (rowsReturned.next()) {
			Site aSite = mapRowToSite(rowsReturned);
			results.add(aSite);
		}
		return results;
	}

	@Override
	public List<Site> getAllSite() {
		// TODO Auto-generated method stub
		return null;
	}
	private Site mapRowToSite(SqlRowSet rowsReturned) {
		Site aSite = new Site();
		aSite.setSite_id(rowsReturned.getInt("site_id"));
		aSite.setCampground_id(rowsReturned.getInt("campground_id"));
		aSite.setSite_number(rowsReturned.getInt("site_number"));
		aSite.setMax_occupancy(rowsReturned.getInt("max_occupancy"));
		aSite.setMax_rv_length(rowsReturned.getInt("max_rv_length"));
		aSite.setUtilities(rowsReturned.getBoolean("utilities"));
		aSite.setAccessible(rowsReturned.getBoolean("accessible"));
	
		
		return aSite;
	}

	@Override
	public List<Site> siteAccomadations(List<Site> sites, int max_occupancy, boolean accessible, int max_rv_length,
			boolean utilities) {
		for (int i = 0; i < sites.size(); i++) {
			Site aSite = sites.get(i);
			if (max_occupancy > aSite.getMax_occupancy() || (accessible && !aSite.getAccessible())
					|| max_rv_length > aSite.getMax_rv_length() || (utilities && !aSite.isUtilities())) {

				sites.remove(i);
				i--;
				
		
			}
		}
		return sites;
	}

}
	//site_id
	//campground_id
	//site_number
	//max_occupancy
	//accessible
	//max_rv_length
	//utilities