package com.ekito.teleinfo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ekito.teleinfo.domain.Mesure;

public interface MesureRepository extends MongoRepository<Mesure, String> {

    public Mesure findById(String id);
    //public List<Mesure> findByLastName(String lastName);

}
