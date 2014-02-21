package com.ekito.teleinfo.repository;

 
 
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ekito.teleinfo.domain.Mesure;

public interface MesureRepository extends MongoRepository<Mesure, String> {

    public Mesure findById(String id);
    //public List<Mesure> findByLastName(String lastName);
 
    @Query("{ 'date' : { '$gt' : ?0 }}")
    public List<Mesure> findByDateGreaterThan(Date date, Sort sort);
    
}
