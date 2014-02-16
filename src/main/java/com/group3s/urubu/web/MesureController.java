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

import com.group3s.urubu.domain.Mesure;
import com.group3s.urubu.repository.MesureRepository;

@Controller
@RequestMapping("/mesure")
public class MesureController {
	
	final static Logger logger = LoggerFactory.getLogger(MesureController.class);

	@Autowired
	MesureRepository repo;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<Mesure> all() {
		logger.info("Listing all ...");
		List<Mesure> all = repo.findAll();
		return all;
	}
	
	 

	private void createMesure(Mesure c) {
		repo.save(c);
		logger.info("saved : "+c);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public @ResponseBody Mesure create(
			@RequestParam(value = "id", required = false) String id,
			
			@RequestParam(value = "adco", required = true) String adco,
			@RequestParam(value = "hchc", required = true) String hchc,
			@RequestParam(value = "hchp", required = true) String hchp,
			@RequestParam(value = "hhphc", required = true) String hhphc,
			@RequestParam(value = "ptec", required = true) String ptec,
			@RequestParam(value = "iinst", required = true) String iinst,
			@RequestParam(value = "papp", required = true) String papp,
			@RequestParam(value = "imax", required = true) String imax,
			@RequestParam(value = "optarif", required = true) String optarif,
			@RequestParam(value = "isousc", required = true) String isousc,
			@RequestParam(value = "motdetat", required = true) String motdetat
		   ) {
		Mesure c = new Mesure(id,adco,new Integer(hchc),new Integer(hchp),hhphc,ptec,new Integer(iinst),new Integer(papp),new Integer(imax),optarif,new Integer(isousc),motdetat);
		createMesure(c);
		return c;
	}
}