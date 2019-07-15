package com.techelevator.models;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcSurveyResultDAO implements SurveyResultDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcSurveyResultDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void addSurvey(SurveyResult aSurvey) {
		String parkCode = aSurvey.getParkCode();
		String emailAddress = aSurvey.getEmailAddress();
		String state = aSurvey.getState();
		String activityLevel = aSurvey.getActivityLevel();
		String sqlAddSurvey = "insert into survey_result(parkcode,emailaddress,state,activitylevel)"
				+ "values (?,?,?,?)";
		jdbcTemplate.update(sqlAddSurvey,parkCode,emailAddress,state,activityLevel);
	}

	@Override
	public int getVotesByParkCode(String parkCode) {
		int votes = 0;
		String sqlGetVotes = "select count(parkcode) AS totalvotes from survey_result where parkcode = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetVotes,parkCode);
		if (results.next()) {
			votes = results.getInt("totalvotes");
		}
		return votes;
	}

	@Override
	public List<String> rankParksByVotes() {
		List<String> surveyResults = new ArrayList<String>();
		String sqlRankParks = "select parkcode, count(parkcode) from survey_result group by parkcode order by 2 desc, parkcode";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlRankParks);
		while (results.next()) {
			surveyResults.add(results.getString("parkcode"));
		}
		return surveyResults;
	}
	
	@SuppressWarnings("unused")
	private SurveyResult mapRowToSurveyResult(SqlRowSet rowReturned) {
		SurveyResult aSurveyResult = new SurveyResult();
		aSurveyResult.setSurveyId(rowReturned.getLong("surveyid"));
		aSurveyResult.setEmailAddress(rowReturned.getString("emailaddress"));
		aSurveyResult.setActivityLevel(rowReturned.getString("activitylevel"));
		aSurveyResult.setParkCode(rowReturned.getString("parkcode"));
		aSurveyResult.setState(rowReturned.getString("state"));
		return aSurveyResult;
	}

}
