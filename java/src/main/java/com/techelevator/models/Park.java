package com.techelevator.models;

public class Park {
	private String parkCode;
	private String parkName;
	String state;
	private long acreage;
	private long elevationInFeet;
	private double milesOfTrail;
	private long numOfCampsites;
	private String climate;
	private long yearFounded;
	private long annualVisitorCount;
	private String inspirationalQuote;
	private String quoteSource;
	private String parkDescription;
	private long entryFee;
	private long numOfAnimalSpecies;
	
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getAcreage() {
		return acreage;
	}
	public void setAcreage(long acreage) {
		this.acreage = acreage;
	}
	public long getElevationInFeet() {
		return elevationInFeet;
	}
	public void setElevationInFeet(long elevationInFeet) {
		this.elevationInFeet = elevationInFeet;
	}
	public double getMilesOfTrail() {
		return milesOfTrail;
	}
	public void setMilesOfTrail(double milesOfTrail) {
		this.milesOfTrail = milesOfTrail;
	}
	public long getNumOfCampsites() {
		return numOfCampsites;
	}
	public void setNumOfCampsites(long numOfCampsites) {
		this.numOfCampsites = numOfCampsites;
	}
	public String getClimate() {
		return climate;
	}
	public void setClimate(String climate) {
		this.climate = climate;
	}
	public long getYearFounded() {
		return yearFounded;
	}
	public void setYearFounded(long yearFounded) {
		this.yearFounded = yearFounded;
	}
	public long getAnnualVisitorCount() {
		return annualVisitorCount;
	}
	public void setAnnualVisitorCount(long annualVisitorCount) {
		this.annualVisitorCount = annualVisitorCount;
	}
	public String getInspirationalQuote() {
		return inspirationalQuote;
	}
	public void setInspirationalQuote(String inspirationalQuote) {
		this.inspirationalQuote = inspirationalQuote;
	}
	public String getQuoteSource() {
		return quoteSource;
	}
	public void setQuoteSource(String quoteSource) {
		this.quoteSource = quoteSource;
	}
	public String getParkDescription() {
		return parkDescription;
	}
	public void setParkDescription(String parkDescription) {
		this.parkDescription = parkDescription;
	}
	public long getEntryFee() {
		return entryFee;
	}
	public void setEntryFee(long entryFee) {
		this.entryFee = entryFee;
	}
	public long getNumOfAnimalSpecies() {
		return numOfAnimalSpecies;
	}
	public void setNumOfAnimalSpecies(long numOfAnimalSpecies) {
		this.numOfAnimalSpecies = numOfAnimalSpecies;
	}
	@Override
	public String toString() {
		return "Park [parkCode=" + parkCode + ", parkName=" + parkName + ", state=" + state + ", acreage=" + acreage
				+ ", elevationInFeet=" + elevationInFeet + ", milesOfTrail=" + milesOfTrail + ", numOfCampsites="
				+ numOfCampsites + ", climate=" + climate + ", yearFounded=" + yearFounded + ", annualVisitorCount="
				+ annualVisitorCount + ", inspirationalQuote=" + inspirationalQuote + ", quoteSource=" + quoteSource
				+ ", parkDescription=" + parkDescription + ", entryFee=" + entryFee + ", numOfAnimalSpecies="
				+ numOfAnimalSpecies + "]";
	}
}
