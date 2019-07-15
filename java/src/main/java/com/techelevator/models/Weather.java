package com.techelevator.models;

import java.util.ArrayList;
import java.util.List;

public class Weather {
	private String parkCode;
	private long fiveDayForecastValue;
	private long low;
	private long high;
	private String forecast;
	
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public long getFiveDayForecastValue() {
		return fiveDayForecastValue;
	}
	public void setFiveDayForecastValue(long fiveDayForecastValue) {
		this.fiveDayForecastValue = fiveDayForecastValue;
	}
	public long getLow() {
		return low;
	}
	public void setLow(long low) {
		this.low = low;
	}
	public long getHigh() {
		return high;
	}
	public void setHigh(long high) {
		this.high = high;
	}
	public String getForecast() {
		return forecast;
	}
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
	public List<String> getWeatherAdvisory() {
		List<String> results = new ArrayList<String>();
		switch(forecast) {
		case "sunny":
			results.add("It's sunny today! Pack some sunscreen and your best smile!");
			break;
		case "rain":
			results.add("It's raining today! Pack rain gear and waterproof shoes!");
			break;
		case "snow":
			results.add("It's snowing today! Pack snowshoes!");
			break;
		case "thunderstorms":
			results.add("It's thundering today! Duck for cover in the nearest shelter and for the love of god don't hike on exposed ridges!");
			break;
		}
		if (high > 75) {
			results.add("Today is quite the hot day! Bring an extra gallon of water!");
		}
		if (low < 20) {
			results.add("Today is too cold to go outside! Exposure to frigid temperatures will literally kill you!!!!");
		}
		if (high - low > 20) {
			results.add("The temperature will vary quite a bit today... wear breathable layers, but don't breathe your layers!");
		}
		return results;
	}
	public String getImgName() {
		switch(forecast) {
		case "cloudy":
			return "cloudy.png";
		case "partly cloudy":
			return "partlyCloudy.png";
		case "sunny":
			return "sunny.png";
		case "rain":
			return "rain.png";
		case "snow":
			return "snow.png";
		case "thunderstorms":
			return "thunderstorms.png";
		}
		return "noPictureHowSad.png";  //in theory, unreachable code
	}
	public long getLowInCelsius() {
		return (low - 32) * 5/9;
	}
	public long getHighInCelsius() {
		return (high - 32) * 5/9;
	}
	@Override
	public String toString() {
		return "Weather [parkCode=" + parkCode + ", fiveDayForecastValue=" + fiveDayForecastValue + ", low=" + low
				+ ", high=" + high + ", forecast=" + forecast + "]";
	}
	
}
