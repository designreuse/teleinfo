package com.ekito.teleinfo.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ekito.teleinfo.TeleinfoApplication;
import com.ekito.teleinfo.domain.Mesure;
import com.ekito.teleinfo.repository.MesureRepository;
 


/**
 * Basic integration tests for demo application.
 * 
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TeleinfoApplication.class})
public class MongoDB {
	
	@Autowired
	MesureRepository repo;

	@Before
	public void berforeTest(){
		//repo.deleteAll();
	}
	
	@Test
	public void testAdd(){
		//Mesure c = new Mesure();
		//repo.save(c);
		
		//List<Mesure> list = repo.findAll();
		
		//Assert.assertEquals("at least 1 Customer in list",1, list.size()); 
	}

}
