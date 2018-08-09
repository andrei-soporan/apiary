/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.persistence.dao;

import java.util.Collection;
import java.util.Date;
import ro.threegsoftware.apiary.exeption.ApiaryException;
import ro.threegsoftware.apiary.model.SensorReadingVO;

/**
 *
 * @author andrei
 */
public interface ISensorReadingDao {
    
    public void insert( SensorReadingVO sensorReading ) throws ApiaryException;
    
    public void update( SensorReadingVO sensorReading ) throws ApiaryException;
    
    public void delete( SensorReadingVO sensorReading ) throws ApiaryException;
    
    public Collection< SensorReadingVO > find( Date startTime, Date endTime ) throws ApiaryException;
}
