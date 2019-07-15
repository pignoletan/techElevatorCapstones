package com.techelevator.models;

public interface WeatherDAO {
	
	public String[] getFiveDayForecast(String parkCode);
	
	public Long[] getFiveDayLow(String parkCode);
	
	public Long[] getFiveDayHigh(String parkCode);
	
	public Weather[] getFiveDayWeather(String parkCode);
	
	//Day must be 1, 2, 3, 4, or 5
	public Weather getWeatherForDay(String parkCode, int day);
	
	public String toString();
}
