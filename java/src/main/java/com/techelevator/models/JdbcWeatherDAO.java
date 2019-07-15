package com.techelevator.models;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcWeatherDAO implements WeatherDAO {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcWeatherDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public String[] getFiveDayForecast(String parkCode) {
		String[] fiveDayForecast = new String[5];
		String sqlSearchByParkCode = "select forecast from weather where parkcode = ? order by fivedayforecastvalue";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchByParkCode,parkCode);
		int i = 0;
		while (results.next()) {
			fiveDayForecast[i] = results.getString("forecast");
			i++;
		}
		return fiveDayForecast;
	}

	@Override
	public Long[] getFiveDayLow(String parkCode) {
		Long[] fiveDayLow = new Long[5];
		String sqlSearchByParkCode = "select low from weather where parkcode = ? order by fivedayforecastvalue";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchByParkCode,parkCode);
		int i = 0;
		while (results.next()) {
			fiveDayLow[i] = results.getLong("low");
			i++;
		}
		return fiveDayLow;
	}

	@Override
	public Long[] getFiveDayHigh(String parkCode) {
		Long[] fiveDayHigh = new Long[5];
		String sqlSearchByParkCode = "select high from weather where parkcode = ? order by fivedayforecastvalue";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchByParkCode,parkCode);
		int i = 0;
		while (results.next()) {
			fiveDayHigh[i] = results.getLong("high");
			i++;
		}
		return fiveDayHigh;
	}
	
	@Override
	public Weather[] getFiveDayWeather(String parkCode) {
		Weather[] fiveDayWeather = new Weather[5];
		String sqlSearchByParkCode = "select * from weather where parkcode = ? order by fivedayforecastvalue";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchByParkCode,parkCode);
		int i = 0;
		while (results.next()) {
			fiveDayWeather[i] = mapRowToWeather(results);
			i++;
		}
		return fiveDayWeather;
	}

	@Override
	public Weather getWeatherForDay(String parkCode, int day) {
		Weather aWeather = new Weather();
		String sqlSearchByParkAndDay = "select * from weather where parkcode = ? and fivedayforecastvalue = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchByParkAndDay,parkCode,day);
		if (results.next()) {
			aWeather = mapRowToWeather(results);
		}
		return aWeather;
	}
	
	private Weather mapRowToWeather(SqlRowSet rowReturned) {
		Weather aWeather = new Weather();
		aWeather.setParkCode(rowReturned.getString("parkcode"));
		aWeather.setFiveDayForecastValue(rowReturned.getLong("fivedayforecastvalue"));
		aWeather.setForecast(rowReturned.getString("forecast"));
		aWeather.setHigh(rowReturned.getLong("high"));
		aWeather.setLow(rowReturned.getLong("low"));
		return aWeather;
	}

}
