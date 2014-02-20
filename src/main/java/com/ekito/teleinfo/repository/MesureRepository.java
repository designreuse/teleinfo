package com.ekito.teleinfo.repository;

 

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ekito.teleinfo.domain.Mesure;

public interface MesureRepository extends MongoRepository<Mesure, String> {

    public Mesure findById(String id);
    //public List<Mesure> findByLastName(String lastName);

}
