package com.group3s.urubu.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.group3s.urubu.UrubuApplication;
import com.group3s.urubu.domain.Customer;
import com.group3s.urubu.repository.CustomerRepository;


/**
 * Basic integration tests for demo application.
 * 
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UrubuApplication.class})
public class MongoDBTest {
	
	@Autowired
	CustomerRepository repo;

	@Before
	public void berforeTest(){
		repo.deleteAll();
	}
	
	@Test
	public void testAdd(){
		Customer c = new Customer("jean", "bond");
		repo.save(c);
		
		List<Customer> list = repo.findAll();
		
		Assert.assertEquals("1 Customer in list",1, list.size()); 
	}

}
