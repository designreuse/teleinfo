
package com.ekito.teleinfo.resources.weather;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("com.googlecode.jsonschema2pojo")
public class Sys {

    private Double message;
    private String country;
    private Integer sunrise;
    private Integer sunset;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Sys withMessage(Double message) {
        this.message = message;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Sys withCountry(String country) {
        this.country = country;
        return this;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public void setSunrise(Integer sunrise) {
        this.sunrise = sunrise;
    }

    public Sys withSunrise(Integer sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public Integer getSunset() {
        return sunset;
    }

    public void setSunset(Integer sunset) {
        this.sunset = sunset;
    }

    public Sys withSunset(Integer sunset) {
        this.sunset = sunset;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
