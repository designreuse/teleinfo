package com.ekito.teleinfo.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

 
public class LocalWeather {
	
	@Id
    private String id;
	
	 private Double temp;
	 private Date sunrise;
	 private Date sunset;
	 private Integer cloud; 
	 private Date date;
	 private String location;
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getTemp() {
		return temp;
	}
	public void setTemp(Double temp) {
		this.temp = temp;
	}
	public Date getSunrise() {
		return sunrise;
	}
	public void setSunrise(Date sunrise) {
		this.sunrise = sunrise;
	}
	public Date getSunset() {
		return sunset;
	}
	public void setSunset(Date sunset) {
		this.sunset = sunset;
	}
	public Integer getCloud() {
		return cloud;
	}
	public void setCloud(Integer cloud) {
		this.cloud = cloud;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	} 
	 

}
