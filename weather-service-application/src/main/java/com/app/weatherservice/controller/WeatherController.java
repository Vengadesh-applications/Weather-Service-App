package com.app.weatherservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.weatherservice.entity.WeatherEntity;
import com.app.weatherservice.entity.WeatherLiveEntity;
import com.app.weatherservice.service.WeatherService;

@Controller
@RequestMapping("/weather")
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/")
	public String getWeatherForecast()  {

		return "WeatherHome";
	}

	@GetMapping("forecast")
	public String getWeatherForecast(@RequestParam(value = "city") String city, Model model)  {

		ResponseEntity<WeatherEntity> response = weatherService.getWeatherForecastByCity(city);

		if(response.getStatusCode() == HttpStatus.OK) {
			
			WeatherEntity weather = response.getBody();
			model.addAttribute("city", weather.getCityName());
			model.addAttribute("weatherDetailsList", weather.getWeatherDetails());
						
			return "WeatherForecast";
		} 
		else {
			return "error";
		}
	}

	@GetMapping("live")
	public String getWeatherLiveUpdate(@RequestParam(value = "city") String city, Model model) {


		ResponseEntity<WeatherLiveEntity> response = weatherService.getWeatherLiveUpdateByCity(city);

		if(response.getStatusCode() == HttpStatus.OK) {

			WeatherLiveEntity weatherObject = response.getBody();

			model.addAttribute("liveData", weatherObject);			

			return "WeatherLive";
		} 
		else {
			return "error";
		}
	}
}
