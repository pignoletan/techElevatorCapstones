package com.techelevator;

import static org.junit.Assert.*;

import org.junit.Test;

import com.techelevator.models.JdbcWeatherDAO;
import com.techelevator.models.Weather;
import com.techelevator.models.WeatherDAO;

public class TestWeatherDao extends DAOIntegrationTest {

	@Test
	public void testGetFiveDayForecast() {
		WeatherDAO weatherDAO = new JdbcWeatherDAO(getDataSource());
		String[] fiveDayForecast = weatherDAO.getFiveDayForecast("GCNP");
		assertEquals("Not all weather values are correct","sunny",fiveDayForecast[0]);
		assertEquals("Not all weather values are correct","partly cloudy",fiveDayForecast[1]);
		assertEquals("Not all weather values are correct","rain",fiveDayForecast[2]);
		assertEquals("Not all weather values are correct","sunny",fiveDayForecast[3]);
		assertEquals("Not all weather values are correct","partly cloudy",fiveDayForecast[4]);
	}
	
	@Test
	public void testGetFiveDayLow() {
		WeatherDAO weatherDAO = new JdbcWeatherDAO(getDataSource());
		Long[] fiveDayLow = weatherDAO.getFiveDayLow("MRNP");
		boolean isEqual = 23 == fiveDayLow[0];
		assertTrue("Not all low values are correct",isEqual);
		isEqual = 24 == fiveDayLow[1];
		assertTrue("Not all low values are correct",isEqual);
		isEqual = 21 == fiveDayLow[2];
		assertTrue("Not all low values are correct",isEqual);
		isEqual = 23 == fiveDayLow[3];
		assertTrue("Not all low values are correct",isEqual);
		isEqual = 21 == fiveDayLow[4];
		assertTrue("Not all low values are correct",isEqual);
	}
	
	@Test
	public void testGetFiveDayHigh() {
		WeatherDAO weatherDAO = new JdbcWeatherDAO(getDataSource());
		Long[] fiveDayHigh = weatherDAO.getFiveDayHigh("ENP");
		boolean isEqual = 82 == fiveDayHigh[0];
		assertTrue("Not all high values are correct",isEqual);
		isEqual = 81 == fiveDayHigh[1];
		assertTrue("Not all high values are correct",isEqual);
		isEqual = 81 == fiveDayHigh[2];
		assertTrue("Not all high values are correct",isEqual);
		isEqual = 82 == fiveDayHigh[3];
		assertTrue("Not all high values are correct",isEqual);
		isEqual = 85 == fiveDayHigh[4];
		assertTrue("Not all high values are correct",isEqual);
	}

	@Test
	public void testGetFiveDayWeather() {
		WeatherDAO weatherDAO = new JdbcWeatherDAO(getDataSource());
		Weather[] weathers = weatherDAO.getFiveDayWeather("GSMNP");
		assertEquals("Can't get the forecast","partly cloudy",weathers[0].getForecast());
		assertEquals("Not all days are properly retried","thunderstorms",weathers[4].getForecast());
		assertEquals("Doesn't get the high properly",74,weathers[2].getHigh());
		assertEquals("Doesn't get the low properly",53,weathers[3].getLow());
		for (int i = 0; i < 5; i++) {
			assertEquals("Doesn't get five days of weather for one park",i+1,weathers[i].getFiveDayForecastValue());
		}
		
	}
	
	@Test
	public void testGetWeatherForDay() {
		JdbcWeatherDAO weatherDAO = new JdbcWeatherDAO(getDataSource());
		assertEquals("Gets incorrected forecast","snow",weatherDAO.getWeatherForDay("GNP", 1).getForecast());
		assertEquals("Gets incorrected low",27,weatherDAO.getWeatherForDay("GNP", 1).getLow());
		assertEquals("Gets incorrected high",40,weatherDAO.getWeatherForDay("GNP", 1).getHigh());
		assertEquals("Only works for day 1","partly cloudy",weatherDAO.getWeatherForDay("GNP", 3).getForecast());
		assertEquals("Only works for some parks","snow",weatherDAO.getWeatherForDay("MRNP", 2).getForecast());
	}
	
}
