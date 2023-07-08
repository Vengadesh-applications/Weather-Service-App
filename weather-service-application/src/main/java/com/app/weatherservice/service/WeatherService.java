package com.app.weatherservice.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.weatherservice.common.Constants;
import com.app.weatherservice.entity.WeatherDetailsEntity;
import com.app.weatherservice.entity.WeatherEntity;
import com.app.weatherservice.entity.WeatherLiveEntity;
import com.app.weatherservice.repository.WeatherLiveRepository;
import com.app.weatherservice.repository.WeatherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

	@Value("${openweathermap.apiKey}")
	private String apiKey;

	@Value("${openweathermap.forecast.apiUrl}")
	String apiUrl;

	@Value("${openweathermap.live.apiUrl}")
	String apiLiveUrl;

	@Autowired
	private WeatherRepository weatherRepo;

	@Autowired
	private WeatherLiveRepository weatherLiveRepo;

	Logger log = LoggerFactory.getLogger(WeatherService.class);

	public ResponseEntity<WeatherLiveEntity> getWeatherLiveUpdateByCity(String city) {

		Optional<WeatherLiveEntity> weatherOpt = weatherLiveRepo.findById(city);	

		if(weatherOpt.isPresent()) {

			WeatherLiveEntity weatherObj = weatherOpt.get();
			LocalDateTime today =  LocalDateTime.now();
			//	log.info("DB time:::="+weatherObj.getDateTime().toLocalTime().getHour());
			//	log.info("Today time:::="+today.getHour());

			if(weatherObj.getDateTime().toLocalTime().getHour() == today.getHour()) {
				
				log.info("Live Weather Data -- Exists in DB");
				return new ResponseEntity<WeatherLiveEntity>(weatherObj, HttpStatus.OK);
			} else {
				return liveApiCall(city, apiLiveUrl);
			}
		} else {
			return liveApiCall(city, apiLiveUrl);
		}
	}

	public ResponseEntity<WeatherLiveEntity> liveApiCall(String city, String api) {

		ResponseEntity<Object> respObj = getWeatherFromApi(city,apiLiveUrl);
		log.info("++ Live Weather Data -- Fetching through API call +++");

		Object tempObj = respObj.getBody();
		WeatherLiveEntity weather = (WeatherLiveEntity) tempObj;

		return new ResponseEntity<WeatherLiveEntity>(weather,HttpStatus.OK);
	}

	public ResponseEntity<WeatherEntity> getWeatherForecastByCity(String city) {


		if(weatherRepo.findByCityName(city)!=null) {

			WeatherEntity weatherObj = weatherRepo.findByCityName(city);
			
			LocalDate today =  LocalDate.now();
			List<LocalDate> dateArray = weatherObj.getWeatherDetails().stream().map(e->e.getDateTime().toLocalDate()).collect(Collectors.toList());

			if(dateArray.contains(today.plusDays(4))) {
				log.info("+++ Forecast Weather Data -- Exists in DB already +++");
				return new ResponseEntity<WeatherEntity>(weatherObj,HttpStatus.OK);			
			} 
			else {
				return	forecastApiCall(city, apiUrl);
			}
		} else {
			return	forecastApiCall(city, apiUrl);

		}
	}

	public ResponseEntity<WeatherEntity> forecastApiCall(String city, String api) {

		ResponseEntity<Object> respObj = getWeatherFromApi(city, apiUrl);
		log.info("Weather Data -- Fetching through API call");

		Object tempObj = respObj.getBody();
		WeatherEntity weather = (WeatherEntity) tempObj;

		return new ResponseEntity<WeatherEntity>(weather,HttpStatus.OK);
	}

	public ResponseEntity<Object> getWeatherFromApi(String city, String api){

		RestTemplate restTemplate = new RestTemplate();
		
		String apiUrlRequested = api + city + Constants.APPID + apiKey;

		ResponseEntity<String> response = restTemplate.getForEntity(apiUrlRequested, String.class);
		log.info("Api called and response received");

		if (response.getStatusCode() == HttpStatus.OK) {

			String weatherJson = response.getBody();
			ResponseEntity<Object> weatherData = parseAndSaveWeatherData(city, weatherJson);
			log.info("Weather details extracted");

			return weatherData;
		} else {
			return ResponseEntity.status(response.getStatusCode()).build();
		}
	}

	public ResponseEntity<Object> parseAndSaveWeatherData(String city, String responseReceived){

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(responseReceived);

			log.info("Inside parse method");

			if(jsonNode.get(Constants.LIST)!=null) {

				WeatherEntity weather = new WeatherEntity();

				weather.setCityName(city);
				List<WeatherDetailsEntity> weatherDetailsList = new ArrayList<>();
				JsonNode forecastNode = jsonNode.get(Constants.LIST);

				for (JsonNode node : forecastNode) {
					WeatherDetailsEntity weatherDetails = new WeatherDetailsEntity();

					long timestamp = node.get(Constants.DATE).asLong();
					LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
					weatherDetails.setDateTime(dateTime);

					int temperature = node.get(Constants.MAIN).get(Constants.TEMPERATURE).asInt();
					String temp = String.valueOf(temperature - 273) + Constants.CELCIUS;
					weatherDetails.setTemperature(temp);

					int humidity = node.get(Constants.MAIN).get(Constants.HUMIDITY).asInt();
					String humid = String.valueOf(humidity) + Constants.PERCENTAGE;
					weatherDetails.setHumidity(humid);

					weatherDetailsList.add(weatherDetails);
				}

				weather.setWeatherDetails(weatherDetailsList);
				log.info("+++ Forecast data saved +++");
				weatherRepo.save(weather);
				
				return ResponseEntity.ok(weather);
			}
			else {

				WeatherLiveEntity weather = new WeatherLiveEntity();
				weather.setCityName(city);

				long timestamp = jsonNode.get(Constants.DATE).asLong();				
				LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
				weather.setDateTime(dateTime);

				int temperature = jsonNode.get(Constants.MAIN).get(Constants.TEMPERATURE).asInt();
				String temp = String.valueOf(temperature - 273) + Constants.CELCIUS;
				weather.setTemperature(temp);

				int humidity = jsonNode.get(Constants.MAIN).get(Constants.HUMIDITY).asInt();
				String humid = String.valueOf(humidity) + Constants.PERCENTAGE;
				weather.setHumidity(humid);

				weatherLiveRepo.save(weather);
				log.info("++ Live data saved / updated!! ++");

				return ResponseEntity.ok(weather);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
