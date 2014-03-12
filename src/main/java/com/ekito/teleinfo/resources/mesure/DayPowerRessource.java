package com.ekito.teleinfo.resources.mesure;

import java.util.Date;

public class DayPowerRessource {

	
	private Date date;
	private Integer hpDayPower;
	private Integer hcDayPower;
	
	private float hpDayCost;
	private float hcDayCost;
	private float baseDayCost;
	private float totalHcHpDayCost;
	private float totalBaseDayCost;
	
	private float periodHcHpTotal;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getHpDayPower() {
		return hpDayPower;
	}
	public void setHpDayPower(Integer hpDayPower) {
		this.hpDayPower = hpDayPower;
	}
	public Integer getHcDayPower() {
		return hcDayPower;
	}
	public void setHcDayPower(Integer hcDayPower) {
		this.hcDayPower = hcDayPower;
	}
	public float getHpDayCost() {
		return hpDayPower * 0.1510F / 1000;
	}
	public void setHpDayCost(float hpDayCost) {
		this.hpDayCost = hpDayCost;
	}
	public float getHcDayCost() {
		return getHcDayPower() * 0.1044F / 1000;
	}
	public void setHcDayCost(float hcDayCost) {
		this.hcDayCost = hcDayCost;
	}
 
	public float getBaseDayCost() {
		return (getHpDayPower() + getHcDayPower()) * 0.1372F / 1000;
	}
	public void setBaseDayCost(float baseDayCost) {
		this.baseDayCost = baseDayCost;
	}
	public float getTotalHcHpDayCost() {
		return  getHcDayCost() + getHpDayCost();
	}
	public void setTotalHcHpDayCost(float totalHcHpDayCost) {
		this.totalHcHpDayCost = totalHcHpDayCost;
	}
	public float getTotalBaseDayCost() {
		return getBaseDayCost();
	}
	public void setTotalBaseDayCost(float totalBaseDayCost) {
		this.totalBaseDayCost = totalBaseDayCost;
	}
	public float getPeriodHcHpTotal() {
		return periodHcHpTotal;
	}
	public void setPeriodHcHpTotal(float periodHcHpTotal) {
		this.periodHcHpTotal = periodHcHpTotal;
	}
	
	
	
}
