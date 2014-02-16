package com.group3s.urubu.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.group3s.urubu.domain.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);

}
