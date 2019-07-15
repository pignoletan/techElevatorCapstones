package com.techelevator.models;

import java.util.List;

public interface SurveyResultDAO {
	public void addSurvey(SurveyResult aSurvey);
	
	public int getVotesByParkCode(String parkCode);
	
	public List<String> rankParksByVotes();
	
}
