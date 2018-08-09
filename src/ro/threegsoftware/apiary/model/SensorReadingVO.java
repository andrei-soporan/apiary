/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.model;

import java.util.Date;

/**
 *
 * @author andrei
 */
public class SensorReadingVO {
    
    private Integer id;

    private String location;
    
    private Date readTime;
    
    private Double temperature;
    
    private Double humidity;
    
    private Double weight;

    
     public SensorReadingVO() {
         
     }
     
    /**
     * Constructor for the value object class
     * 
     * @param id
     * @param location
     * @param readTime
     * @param temperature
     * @param humidity
     * @param weight
     */
    public SensorReadingVO( Integer id, String location, Date readTime, Double temperature, Double humidity, Double weight ) {
        this.id = id;
        this.location = location;
        this.readTime = readTime;
        this.temperature = temperature;
        this.humidity = humidity;
        this.weight = weight;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
