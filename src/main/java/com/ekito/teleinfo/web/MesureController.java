package com.ekito.teleinfo.web;

 
import java.util.Date;
import java.util.Iterator;
import java.util.List;
 


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ekito.teleinfo.domain.Mesure;
import com.ekito.teleinfo.repository.MesureRepository;

@Controller
@RequestMapping("/mesure")
public class MesureController {
	
	final static Logger logger = LoggerFactory.getLogger(MesureController.class);
	

	@Autowired
	MesureRepository mesureRepo;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<Mesure> all() {
		logger.info("Listing all ...");
		List<Mesure> all = mesureRepo.findAll();
		return all;
	}
	 
	@RequestMapping(value = "/graphall", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody String all_mesures() {
		logger.info("Listing all ...");
		List<Mesure> all = mesureRepo.findAll();
		//List<String> allString = new ArrayList<String>();
		String allString ="";
		Iterator<Mesure> iterator = all.iterator();
		while (iterator.hasNext()) {
			
			Mesure mesure = iterator.next();
			Date date =  mesure.getDate();
			if (date!=null)
			allString += "["+date.getTime()+","+ mesure.getPapp()+"],";
			
		}
		
		
		return "?(["+allString+"]);";
	}
	
	 

	private void createMesure(Mesure c) {
		mesureRepo.save(c);
		logger.info("saved : "+c);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public @ResponseBody Mesure create(
			@RequestParam(value = "id", required = false) String id,
			
			@RequestParam(value = "adco", required = true) String adco,
			@RequestParam(value = "hchc", required = true) Integer hchc,
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