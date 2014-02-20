package com.ekito.teleinfo.repository;

 

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ekito.teleinfo.domain.LocalWeather;


public interface WeatherRepository extends MongoRepository<LocalWeather, String> {

    public LocalWeather findById(String id);
  
}
