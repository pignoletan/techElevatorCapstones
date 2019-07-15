package com.techelevator.npgeek;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techelevator.models.Park;
import com.techelevator.models.ParkDAO;
import com.techelevator.models.SurveyResult;
import com.techelevator.models.SurveyResultDAO;
import com.techelevator.models.Weather;
import com.techelevator.models.WeatherDAO;


@Controller
public class HomeController {
	
	@Autowired
	private ParkDAO parkDAO;
	@Autowired
	private WeatherDAO weatherDAO;
	@Autowired
	private SurveyResultDAO surveyResultDAO;

	
//	@RequestMapping("/")
//	public String initializeNationalParkGeek(HttpSession session) {
//		session.setAttribute("isFahrenheit", true);
//		return "redirect:/homePage";
//	}
	
	@RequestMapping("/")
	public String displayHomePage(ModelMap mapHolder) {
		List<Park> allParks = parkDAO.getAllParks();
		mapHolder.put("allParks", allParks);
		return "homePage";
	}
	
	@RequestMapping(path="/surveyInput", method=RequestMethod.GET)
	public String displaySurveyInput(Model SurveyResult) {         // Define ModelMap called modelHolder (refer to comments at end of this file if you are confused about Model vs ModelMap
		if( ! SurveyResult.containsAttribute("SurveyResult")) {        // If the modelHolder ModelMap does not have a "SignUp" entry
			SurveyResult.addAttribute("SurveyResult", new SurveyResult());   //    add one
		}
		SurveyResult.addAttribute("parks",parkDAO.getAllParks());
		return "surveyInput";
	}
	
	@RequestMapping(path="/surveyInput",method=RequestMethod.POST)
	public String inputSurvey(@Valid @ModelAttribute("SurveyResult") SurveyResult registerSurveyValues,
		BindingResult result, RedirectAttributes flashData) {
		if (result.hasErrors()) {
			flashData.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "SurveyResult", result);
			flashData.addFlashAttribute("SurveyResult", registerSurveyValues);
			return "redirect:/surveyInput";
		}
		surveyResultDAO.addSurvey(registerSurveyValues);
		return "redirect:/surveyResult";
	}
	
	@RequestMapping("/surveyResult")
	public String displaySurveyResult(ModelMap modelMap){
		List<String> winningParkCodes = surveyResultDAO.rankParksByVotes();
		List<String> winningParks = new ArrayList<String>();
		for (String parkCode : winningParkCodes) {
			Park aPark = parkDAO.getParkByCode(parkCode);
			String parkAndVotes = aPark.getParkName() + " - " + Integer.toString(surveyResultDAO.getVotesByParkCode(parkCode));
			winningParks.add(parkAndVotes);
		}
		modelMap.addAttribute("winningParks",winningParks);
		return "surveyResult";
	}
	
	@RequestMapping("/parkDetail")
	public String displayParkDetails(@RequestParam String parkCode, ModelMap modelMap, HttpSession session) {
		if (session.getAttribute("isFahrenheit") == null) {
			session.setAttribute("isFahrenheit", true);
		}
		Park aPark = parkDAO.getParkByCode(parkCode);
		modelMap.put("park",aPark);
		Weather[] fiveDayWeather = weatherDAO.getFiveDayWeather(parkCode);
		modelMap.put("fiveDayForecast", fiveDayWeather);
		return "parkDetail";
	}
	
	@RequestMapping("/changeTemp")
	public String switchToCelsius(HttpSession session, @RequestParam String parkCode,
			@RequestParam boolean isFahrenheit) {
		session.setAttribute("isFahrenheit", isFahrenheit);
		String result = "redirect:/parkDetail?parkCode=" + parkCode;
		System.out.println(result);
		return result;
	}
	
	@RequestMapping("/fahrenheit")
	public String switchToFahrenheit(HttpSession session, @RequestParam String parkCode) {
		session.setAttribute("isFahrenheit", true);
		return "redirect:/parkDetail?parkCode=" + parkCode;
	}
	
}
