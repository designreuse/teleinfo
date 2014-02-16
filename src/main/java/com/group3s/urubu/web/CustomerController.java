package com.group3s.urubu.web;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group3s.urubu.domain.Customer;
import com.group3s.urubu.repository.CustomerRepository;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CustomerRepository repo;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<Customer> all() {
		logger.info("Listing all ...");
		List<Customer> all = repo.findAll();
		return all;
	}
	
	@RequestMapping(value = "/random", method = RequestMethod.GET)
	public @ResponseBody Customer random() {
		Customer c = new Customer(""+UUID.randomUUID(), ""+System.currentTimeMillis());
		createCustomer(c);
		return c;
	}

	private void createCustomer(Customer c) {
		repo.save(c);
		logger.info("saved : "+c);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody Customer create(
			@RequestParam(value = "firstName", required = true) String firstName,
			@RequestParam(value = "lastName", required = true) String lastName) {
		Customer c = new Customer(firstName, lastName);
		createCustomer(c);
		return c;
	}
}