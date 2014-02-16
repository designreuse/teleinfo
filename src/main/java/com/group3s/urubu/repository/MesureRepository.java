package com.group3s.urubu.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.group3s.urubu.domain.Customer;
import com.group3s.urubu.domain.Mesure;

public interface MesureRepository extends MongoRepository<Mesure, String> {

    public Mesure findById(String id);
    //public List<Mesure> findByLastName(String lastName);

}
