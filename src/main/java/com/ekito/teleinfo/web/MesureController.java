package com.ekito.teleinfo.web;

 
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ekito.teleinfo.domain.LocalWeather;
import com.ekito.teleinfo.domain.Mesure;
import com.ekito.teleinfo.repository.MesureRepository;
import com.ekito.teleinfo.repository.WeatherRepository;
import com.ekito.teleinfo.resources.mesure.MesureRessource;
import com.ekito.teleinfo.resources.mesure.Page;


@Controller
 
@RequestMapping("/mesure")
public class MesureController {
	
	final static Logger logger = LoggerFactory.getLogger(MesureController.class);
    

	@Autowired
	MesureRepository mesureRepo;
	
	@Autowired
	WeatherController weatherController;
	
	@Autowired
	WeatherRepository weatherRepo;

 
	@RequestMapping(value = "/deleteAllNull", method = RequestMethod.GET)
	public @ResponseBody void deleteAllNull() {
		logger.info("deleteAllNull...");
		List<Mesure> all = mesureRepo.findByDateNull();
		mesureRepo.delete(all);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<Mesure> all() {
		logger.info("Listing all ...");
		List<Mesure> all = mesureRepo.findAll();
	
		return all;
	}
	 
	 
 
	
	@RequestMapping(value = "/allDetail", method = RequestMethod.GET)
	public @ResponseBody Page allDetail() {
		logger.info("Listing allDetail ...");
		
	  	
		
		List<Mesure> all = mesureRepo.findAll(new Sort(Sort.Direction.ASC, "date"));
		List<MesureRessource> mesureRessources = new ArrayList<MesureRessource>();	
		Iterator<Mesure> iterator = all.iterator();
		while (iterator.hasNext()) {
			
			Mesure mesure = iterator.next();
			mesureRessources.add(new MesureRessource(mesure.getPtec(), mesure.getPapp(), mesure.getDate()));
		}
		
	
		List<LocalWeather> weather = weatherRepo.findAll(new Sort(Sort.Direction.ASC, "date"));
		
		Page page = new Page();
		page.setAll(mesureRessources);
		
		page.setWeather(weather);
		
		return page;
	}
	
	@RequestMapping(value = "/intradayDetail", method = RequestMethod.GET)
	public @ResponseBody Page intradayDetail() {
		logger.info("Listing intradayDetail ...");
		
 
		Calendar calendar = Calendar.getInstance();
	    Calendar startDay = new GregorianCalendar(calendar.get( Calendar.YEAR ),calendar.get( Calendar.MONTH ),calendar.get( Calendar.DAY_OF_MONTH ));
		

		logger.info("today we are: "+ new Date(startDay.getTimeInMillis()));

		
		List<Mesure> all = mesureRepo.findByDateGreaterThan(new Date(startDay.getTimeInMillis()), new Sort(Sort.Direction.ASC, "date"));
		 
		List<MesureRessource> mesureRessources = new ArrayList<MesureRessource>();	
		Iterator<Mesure> iterator = all.iterator();
		while (iterator.hasNext()) {
			
			Mesure mesure = iterator.next();
			mesureRessources.add(new MesureRessource(mesure.getPtec(), mesure.getPapp(), mesure.getDate()));
		}
		
		List<LocalWeather> weather = weatherRepo.findByDateGreaterThan(new Date(startDay.getTimeInMillis()), new Sort(Sort.Direction.ASC, "date"));
		
		Page page = new Page();
		page.setAll(mesureRessources);
		
		page.setWeather(weather);
		
		return page;
	}
	
	@RequestMapping(value = "/fourDaysDetail", method = RequestMethod.GET)
	public @ResponseBody Page fourDaysDetail() {
		logger.info("Listing intradayDetail ...");
		
		Date fourdaysBefore = new Date(new Date().getTime()-4*24*3600*1000);
	
		List<Mesure> all = mesureRepo.findByDateGreaterThan(fourdaysBefore, new Sort(Sort.Direction.ASC, "date"));
		 
		List<MesureRessource> mesureRessources = new ArrayList<MesureRessource>();	
		Iterator<Mesure> iterator = all.iterator();
		while (iterator.hasNext()) {
			
			Mesure mesure = iterator.next();
			mesureRessources.add(new MesureRessource(mesure.getPtec(), mesure.getPapp(), mesure.getDate()));
		}
		
		List<LocalWeather> weather = weatherRepo.findByDateGreaterThan(fourdaysBefore, new Sort(Sort.Direction.ASC, "date"));
		
		Page page = new Page();
		page.setAll(mesureRessources);
		
		page.setWeather(weather);
		
		return page;
	}
 
	 
	 
	
	@RequestMapping(value = "/initMesureFromServer", method = RequestMethod.GET, produces = "text/javascript;")
	public @ResponseBody void initMesures(@RequestParam(value = "server", required = true) String server) {
		 RestTemplate restTemplate = new RestTemplate();
		
		 class ListMesure extends ArrayList<Mesure>  {  
			 
		 }
		 
		 Mesure[] mesures = restTemplate.getForObject("http://"+server+"/mesure/all", new Mesure[0].getClass());
		
		 logger.info("init from server : " + server);
		 
		 
		 //mesureRepo.deleteAll();
		 for (int i=0;i< mesures.length ; i++ ){
			 mesureRepo.save(mesures[i]);
			 
		 }
		 logger.info("init from server, save "+mesures.length+" mesures");
		
	}
	
	@RequestMapping(value = "/initAllFromServer", method = RequestMethod.GET, produces = "text/javascript;")
	public @ResponseBody void initAll(@RequestParam(value = "server", required = true) String server) {
		
		this.initMesures(server);
		weatherController.initWeather(server);
		 
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