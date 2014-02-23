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
import com.ekito.teleinfo.resources.mesure.Papp;


@Controller
@RequestMapping("/mesure")
public class MesureController {
	
	final static Logger logger = LoggerFactory.getLogger(MesureController.class);
    

	@Autowired
	MesureRepository mesureRepo;
	
	@Autowired
	WeatherRepository weatherRepo;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<Mesure> all() {
		logger.info("Listing all ...");
		List<Mesure> all = mesureRepo.findAll();
	
		return all;
	}
	 
	@RequestMapping(value = "/pappIntraday", method = RequestMethod.GET)
	public @ResponseBody Papp pappIntraday() {
		logger.info("Listing pappIntraday ...");
		
		Calendar calendar = Calendar.getInstance();
	    Calendar startDay = new GregorianCalendar(calendar.get( Calendar.YEAR ),calendar.get( Calendar.MONTH ),calendar.get( Calendar.DAY_OF_MONTH ));
		

		logger.info("today we are: "+ new Date(startDay.getTimeInMillis()));

		List<Mesure> hc = mesureRepo.findByDateGreaterThanOnlyHC(new Date(startDay.getTimeInMillis()),new Sort(Sort.Direction.ASC, "date"));
		List<Mesure> hp = mesureRepo.findByDateGreaterThanOnlyHP(new Date(startDay.getTimeInMillis()),new Sort(Sort.Direction.ASC, "date"));
		
		Papp papp = new Papp();
		papp.setHchc(hc);
		papp.setHchp(hp);
		
		return papp;
	}
	
	
	@RequestMapping(value = "/pappAll", method = RequestMethod.GET)
	public @ResponseBody Papp pappAll() {
		logger.info("Listing pappAll ...");
		
	  
		List<Mesure> hc = mesureRepo.findOnlyHC(new Date(0),new Sort(Sort.Direction.ASC, "date"));
		List<Mesure> hp = mesureRepo.findOnlyHP(new Date(0),new Sort(Sort.Direction.ASC, "date"));
		
		List<LocalWeather> weather = weatherRepo.findAll();
		
		Papp papp = new Papp();
		papp.setHchc(hc);
		papp.setHchp(hp);
		papp.setWeather(weather);
		
		return papp;
	}
	
	
	

	@RequestMapping(value = "/intraday", method = RequestMethod.GET)
	public @ResponseBody List<Mesure> intraday() {
		logger.info("Listing intraday ...");
		
		Calendar calendar = Calendar.getInstance();
	    Calendar startDay = new GregorianCalendar(calendar.get( Calendar.YEAR ),calendar.get( Calendar.MONTH ),calendar.get( Calendar.DAY_OF_MONTH ));
		

		logger.info("today we are: "+ new Date(startDay.getTimeInMillis()));

		List<Mesure> all = mesureRepo.findByDateGreaterThan(new Date(startDay.getTimeInMillis()),new Sort(Sort.Direction.ASC, "date"));
		return all;
	}
	 
	@RequestMapping(value = "/graphintraday", method = RequestMethod.GET, produces = "text/javascript;")
	public @ResponseBody String mesuresIntraday(@RequestParam(value = "callback", required = true) String callback) {
		logger.info("Listing all ...");
	    
		Calendar calendar = Calendar.getInstance();
	    Calendar startDay = new GregorianCalendar(calendar.get( Calendar.YEAR ),calendar.get( Calendar.MONTH ),calendar.get( Calendar.DAY_OF_MONTH ));
		

		List<Mesure> all = mesureRepo.findByDateGreaterThan(new Date(startDay.getTimeInMillis()),new Sort(Sort.Direction.ASC, "date"));
		//List<String> allString = new ArrayList<String>();
		String allString ="";
		Iterator<Mesure> iterator = all.iterator();
		while (iterator.hasNext()) {
			
			Mesure mesure = iterator.next();
			Date date =  mesure.getDate();
			if (date!=null)
			allString += "["+date.getTime()+","+ mesure.getPapp()+"],";
			
		}
		
		
		return callback+"(["+allString.replaceFirst("^*(,)$", "")+"]);";
	}
	
	@RequestMapping(value = "/initFromServer", method = RequestMethod.GET, produces = "text/javascript;")
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
		 logger.info("init from server, save "+mesures.length+" weathers");
		 /*
			 
		 class ListWeather extends ArrayList<LocalWeather>  {  
		 }
		 
		 LocalWeather[] localweathers = restTemplate.getForObject("http://54.246.90.43/weather/all", new LocalWeather[0].getClass());
		
		 logger.info("reset all weathers");
		 weatherRepo.deleteAll();
		 for (int i=0;i< localweathers.length ; i++ ){
			 weatherRepo.save(localweathers[i]);
			 
		 }
		 logger.info("init from server, save "+localweathers.length+" mesures");
		 */
	}
	
	@RequestMapping(value = "/graphall", method = RequestMethod.GET, produces = "text/javascript;")
	public @ResponseBody String all_mesures(@RequestParam(value = "callback", required = true) String callback) {
		logger.info("Listing all ...");
		List<Mesure> all = mesureRepo.findAll(new Sort(Sort.Direction.ASC, "date"));
		//List<String> allString = new ArrayList<String>();
		String allString ="";
		Iterator<Mesure> iterator = all.iterator();
		while (iterator.hasNext()) {
			
			Mesure mesure = iterator.next();
			Date date =  mesure.getDate();
			if (date!=null)
			allString += "["+date.getTime()+","+ mesure.getPapp()+"],";
			
		}
		
		
		return callback+"(["+allString.replaceFirst("^*(,)$", "")+"]);";
	}
	
	@RequestMapping(value = "/graphall_hphc", method = RequestMethod.GET, produces = "text/javascript;")
	public @ResponseBody String all_mesures_hphc(@RequestParam(value = "callback", required = true) String callback) {
		logger.info("Listing all ...");
		List<Mesure> all = mesureRepo.findAll(new Sort(Sort.Direction.ASC, "date"));
		//List<String> allString = new ArrayList<String>();
		String allString ="";
		Iterator<Mesure> iterator = all.iterator();
		Mesure previousMesure = null;
		boolean tarifHC= false;
		Integer pappHC=0,pappHP=0;
		while (iterator.hasNext()) {
			
			Mesure mesure = iterator.next();
			Date date =  mesure.getDate();
			if (previousMesure != null && mesure != null )
				if (mesure.getHchc()!= null && previousMesure.getHchc()!= null)
			{
				 
				if (mesure.getHchc() > 
				previousMesure.getHchc()) 
					tarifHC = true; else tarifHC=false;
			}
			if (tarifHC) {
				pappHC = mesure.getPapp(); 
				pappHP = 0;
			}
			else
			{
				pappHC = 0;
				pappHP = mesure.getPapp();
			}
			if (date!=null)
			{
				
			allString += "["+date.getTime()+","+ pappHC+"],";
			}
			previousMesure = mesure;
		}
		
		
		return callback+"(["+allString.replaceFirst("^*(,)$", "")+"]);";
	}
	
	@RequestMapping(value = "/graphintraday_hphc", method = RequestMethod.GET, produces = "text/javascript;")
	public @ResponseBody String intraday_mesures_hphc(@RequestParam(value = "callback", required = true) String callback) {
		logger.info("Listing all ...");
		List<Mesure> all = mesureRepo.findAll(new Sort(Sort.Direction.ASC, "date"));
		//List<String> allString = new ArrayList<String>();
		String allString ="";
		Iterator<Mesure> iterator = all.iterator();
		Mesure previousMesure = null;
		boolean tarifHC= false;
		Integer pappHC=0,pappHP=0;
		while (iterator.hasNext()) {
			
			Mesure mesure = iterator.next();
			Date date =  mesure.getDate();
			if (previousMesure != null && mesure != null )
				if (mesure.getHchc()!= null && previousMesure.getHchc()!= null)
			{
				 
				if (mesure.getHchc() > 
				previousMesure.getHchc()) 
					tarifHC = true; else tarifHC=false;
			}
			if (tarifHC) {
				pappHC = mesure.getPapp(); 
				pappHP = 0;
			}
			else
			{
				pappHC = 0;
				pappHP = mesure.getPapp();
			}
			if (date!=null)
			{
				
			allString += "["+date.getTime()+","+ pappHC+"],";
			}
			previousMesure = mesure;
		}
		
		
		return callback+"(["+allString.replaceFirst("^*(,)$", "")+"]);";
	}
	
	
	@RequestMapping(value = "/graphall_hphp", method = RequestMethod.GET, produces = "text/javascript;")
	public @ResponseBody String all_mesures_hphp(@RequestParam(value = "callback", required = true) String callback) {
		logger.info("Listing all ...");
		List<Mesure> all = mesureRepo.findAll(new Sort(Sort.Direction.ASC, "date"));
		//List<String> allString = new ArrayList<String>();
		String allString ="";
		Iterator<Mesure> iterator = all.iterator();
		Mesure previousMesure = null;
		boolean tarifHC= false;
		Integer pappHC=0,pappHP=0;
		while (iterator.hasNext()) {
			
			Mesure mesure = iterator.next();
			Date date =  mesure.getDate();
			if (previousMesure != null && mesure != null )
				if (mesure.getHchc()!= null && previousMesure.getHchc()!= null)
			{
				 
				if (mesure.getHchc() > 
				previousMesure.getHchc()) 
					tarifHC = true; else tarifHC=false;
			}
			if (tarifHC) {
				pappHC = mesure.getPapp(); 
				pappHP = 0;
			}
			else
			{
				pappHC = 0;
				pappHP = mesure.getPapp();
			}
			if (date!=null)
			{
				
			allString += "["+date.getTime()+","+ pappHP+"],";
			}
			previousMesure = mesure;
		}
		
		
		return callback+"(["+allString.replaceFirst("^*(,)$", "")+"]);";
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