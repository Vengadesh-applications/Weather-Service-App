package com.app.weatherservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.weatherservice.entity.WeatherLiveEntity;

@Repository
public interface WeatherLiveRepository extends JpaRepository<WeatherLiveEntity, String>  {

}
