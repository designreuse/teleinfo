
package com.ekito.teleinfo.resources.weather;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Generated("com.googlecode.jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Clouds {

    private Integer all;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    public Clouds withAll(Integer all) {
        this.all = all;
        return this;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
