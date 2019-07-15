package com.techelevator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.techelevator.models.JdbcParkDAO;
import com.techelevator.models.Park;
import com.techelevator.models.ParkDAO;

public class TestParkDao extends DAOIntegrationTest {
	
	@Test
	public void testGetParkById() {
		ParkDAO parkDao = new JdbcParkDAO(getDataSource());
		Park aPark = parkDao.getParkByCode("GNP");
		assertEquals("GNP returns something wrong","Glacier National Park",aPark.getParkName());
		aPark = parkDao.getParkByCode("YNP");
		assertEquals("YNP returns something wrong","Yellowstone National Park",aPark.getParkName());
		aPark = parkDao.getParkByCode("YNP2");
		assertEquals("YNP2 returns something wrong","Yosemite National Park",aPark.getParkName());
	}
	
	@Test
	public void testGetAllParks() {
		ParkDAO parkDao = new JdbcParkDAO(getDataSource());
		List<Park> allParks = parkDao.getAllParks();
		assertEquals("Did not return 10 parks as expected",10,allParks.size());
		int sampleParks = 0; //try to find 3 specific parks, since manually checking for all ten would obnoxious
		for (Park aPark : allParks) {
			String aName = aPark.getParkName();
			if (aName.contentEquals("Mount Rainier National Park")
					||aName.contentEquals("Great Smoky Mountains National Park")
					||aName.contentEquals("Cuyahoga Valley National Park")) {
				sampleParks += 1;
			}
		}
		assertEquals("Some parks are not present, or their name is wrong.",3,sampleParks);
	}

}
