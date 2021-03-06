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
 
    @Query("{ 'date' : { $gt : ?0 }}")
    public List<Mesure> findByDateGreaterThan(Date date, Sort sort);
    
    @Query("{ 'date' : { '$gt' : ?0 } , 'ptec' : 'HC..'}")
    public List<Mesure> findByDateGreaterThanOnlyHC(Date date, Sort sort);
    
    @Query("{ 'date' : { '$gt' : ?0 } , 'ptec' : { $ne: 'HC..' }}")
    public List<Mesure> findByDateGreaterThanOnlyHP(Date date, Sort sort);
    
    @Query("{ 'ptec' : 'HC..'}")
    public List<Mesure> findOnlyHC(Date date, Sort sort);
    
    @Query("{ 'ptec' :  { $ne: 'HC..' }}")
    public List<Mesure> findOnlyHP(Date date, Sort sort);
    
    @Query("{ 'date' :  null }")
    public List<Mesure> findByDateNull();
 
    
}
