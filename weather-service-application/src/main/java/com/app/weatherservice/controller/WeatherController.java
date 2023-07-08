package com.app.weatherservice.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.weatherservice.entity.WeatherEntity;
import com.app.weatherservice.entity.WeatherLiveEntity;
import com.app.weatherservice.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {

	@Autowired
	private WeatherService weatherService;


	@GetMapping("forecast/{city}")
	public ResponseEntity<WeatherEntity> getWeatherForecast(@PathVariable String city)  {

		return weatherService.getWeatherForecastByCity(city);
	}
	
	@GetMapping("live/{city}")
	public ResponseEntity<WeatherLiveEntity> getWeatherLiveUpdate(@PathVariable String city) {

		return weatherService.getWeatherLiveUpdateByCity(city);
	}
}
