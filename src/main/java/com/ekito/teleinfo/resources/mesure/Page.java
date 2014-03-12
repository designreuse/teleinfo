package com.ekito.teleinfo.resources.mesure;

import java.util.ArrayList;
import java.util.List;

import com.ekito.teleinfo.domain.LocalWeather;
import com.ekito.teleinfo.domain.Mesure;
 

public class Page {

	List<MesureRessource> all = new ArrayList<MesureRessource>();	 
	List<LocalWeather> weather = new ArrayList<LocalWeather>();
	List<DayPowerRessource> dayPower = new ArrayList<DayPowerRessource>();
	
	 
	public List<LocalWeather> getWeather() {
		return weather;
	}
	public void setWeather(List<LocalWeather> weather) {
		this.weather = weather;
	}
	public List<MesureRessource> getAll() {
		return all;
	}
	public void setAll(List<MesureRessource> all) {
		this.all = all;
	}
	public List<DayPowerRessource> getDayPower() {
		return dayPower;
	}
	public void setDayPower(List<DayPowerRessource> dayPower) {
		this.dayPower = dayPower;
	}
}
