package com.ekito.teleinfo.repository;

 

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ekito.teleinfo.domain.LocalWeather;


public interface WeatherRepository extends MongoRepository<LocalWeather, String> {

    public LocalWeather findById(String id);
    
    @Query("{ 'date' : { '$gt' : ?0 }}")
    public List<LocalWeather> findByDateGreaterThan(Date date, Sort sort);
  
}
