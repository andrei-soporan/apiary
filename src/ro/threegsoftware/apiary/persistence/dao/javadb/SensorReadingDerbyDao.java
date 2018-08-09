/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.persistence.dao.javadb;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import ro.threegsoftware.apiary.exeption.ApiaryException;
import ro.threegsoftware.apiary.model.SensorReadingVO;
import ro.threegsoftware.apiary.persistence.DataSourceFactory;
import ro.threegsoftware.apiary.persistence.dao.ISensorReadingDao;
import ro.threegsoftware.apiary.util.DateHelper;

/**
 *
 * @author andrei
 */
public class SensorReadingDerbyDao implements ISensorReadingDao {

    private final static Logger log = Logger.getLogger(SensorReadingDerbyDao.class.getName());
    
    private final static String INSERT_SENSOR_READING = 
            "insert into SENSOR_READING(ID, LOCATION, READ_TIME, TEMPERATURE, HUMIDITY, WEIGHT) values (?,?,?,?,?,?)";

    public SensorReadingDerbyDao() {
    }
    
    @Override
    public void insert(SensorReadingVO sensorReading) throws ApiaryException {
        Connection dbConnection;
        
        try {
            dbConnection = DataSourceFactory.getDataSource().getConnection();
        } catch (SQLException ex) {
            throw new ApiaryException(ex.getMessage());
        }
        
        CallableStatement cs = null;
        
        if ( sensorReading.getId() == null ) {
            sensorReading.setId( DateHelper.getCurrentLongDate().intValue() );
        }

        try 
        {
            cs = dbConnection.prepareCall( INSERT_SENSOR_READING );
            cs.setInt(1, sensorReading.getId());
            
            if ( sensorReading.getLocation() != null ) {
                cs.setString(2, sensorReading.getLocation());
            } else {
                cs.setNull(2, java.sql.Types.VARCHAR );
            }
            
            
            if ( sensorReading.getReadTime() != null ) {
                cs.setTimestamp(3, new java.sql.Timestamp( sensorReading.getReadTime().getTime() ) );
            } else {
                cs.setNull(3, java.sql.Types.TIMESTAMP );
            }
                
            
            if ( sensorReading.getTemperature() != null ) {
                cs.setDouble(4, sensorReading.getTemperature());
            } else {
                cs.setNull(4, java.sql.Types.DOUBLE);
            }
            
            if ( sensorReading.getHumidity() != null ) {
                cs.setDouble(5, sensorReading.getHumidity());
            } else {
                cs.setNull(5, java.sql.Types.DOUBLE);
            }
            
            if ( sensorReading.getWeight() != null ) {
                cs.setDouble(6, sensorReading.getWeight());
            } else {
                cs.setNull(6, java.sql.Types.DOUBLE);
            }
            
            cs.execute();
            cs.close();
            
            dbConnection.commit();
            
        } catch ( SQLException ex ) {
            
            log.severe( ex.getMessage() );
            
            try {
                cs.close();
                dbConnection.rollback();
            } catch (SQLException ex1) {
                log.severe( ex1.getMessage() );
            }
        }
    }

    @Override
    public void update(SensorReadingVO sensorReading) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(SensorReadingVO sensorReading) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<SensorReadingVO> find(Date startTime, Date endTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Exception ApiaryException(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
