package com.app.weatherservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.weatherservice.entity.WeatherEntity;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long>  {

	
	WeatherEntity findByCityName(String city);
}
