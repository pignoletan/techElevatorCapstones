package com.techelevator.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class SurveyResult {
	
	private long surveyId;
	
	private String parkCode;
	@NotBlank(message="How will we notify you when you win?")
	@Email(message="You can't fool us with a fake e-mail address that easily!")
	private String emailAddress;
	private String state;
	private String activityLevel;
	public long getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(long surveyId) {
		this.surveyId = surveyId;
	}
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getActivityLevel() {
		return activityLevel;
	}
	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}
	@Override
	public String toString() {
		return "SurveyResult [surveyId=" + surveyId + ", parkCode=" + parkCode + ", emailAddress=" + emailAddress
				+ ", state=" + state + ", activityLevel=" + activityLevel + "]";
	}
	
}
