package com.ekito.teleinfo.resources.mesure;

import java.util.ArrayList;
import java.util.List;

import com.ekito.teleinfo.domain.LocalWeather;
import com.ekito.teleinfo.domain.Mesure;
 

public class Papp {

	
	List<Mesure> hchc = new ArrayList<Mesure>();
	List<Mesure> hchp = new ArrayList<Mesure>();
	List<LocalWeather> weather = new ArrayList<LocalWeather>();
	
	
	public List<Mesure> getHchc() {
		return hchc;
	}
	public void setHchc(List<Mesure> hchc) {
		this.hchc = hchc;
	}
	public List<Mesure> getHchp() {
		return hchp;
	}
	public void setHchp(List<Mesure> hchp) {
		this.hchp = hchp;
	}
	public List<LocalWeather> getWeather() {
		return weather;
	}
	public void setWeather(List<LocalWeather> weather) {
		this.weather = weather;
	}
}
