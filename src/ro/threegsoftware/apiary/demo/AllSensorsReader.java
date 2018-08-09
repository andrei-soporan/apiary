/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.demo;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.Spi;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import ro.threegsoftware.apiary.exeption.ApiaryException;
import ro.threegsoftware.apiary.extension.Mcp3008;
import ro.threegsoftware.apiary.extension.exception.Mcp3008Exception;
import ro.threegsoftware.apiary.model.SensorReadingVO;
import ro.threegsoftware.apiary.persistence.dao.ISensorReadingDao;
import ro.threegsoftware.apiary.persistence.dao.SensorReadingDaoFactory;
import ro.threegsoftware.apiary.sensor.Rht03;
import ro.threegsoftware.apiary.sensor.exception.Rht03Exception;
import ro.threegsoftware.apiary.util.DateHelper;

/**
 *
 * @author andrei
 */
public class AllSensorsReader {
    
    private static Logger log = Logger.getLogger(AllSensorsReader.class.getName());
    
    public static void testDao() {
        SensorReadingVO  reading = new SensorReadingVO();
        reading.setLocation("Test hive");
        reading.setTemperature(1.0);
        reading.setHumidity(2.0);
        reading.setReadTime(DateHelper.getCurrentDate());

        ISensorReadingDao sensorDao = SensorReadingDaoFactory.GetDao();
        try {
            sensorDao.insert(reading);
        } catch (ApiaryException ex) {
            log.severe(ex.getMessage());
        }
    }
    
    public static void readSensorsToDb() {
        System.out.println("<--Pi4J--> Reading from MCP3008 IC ... started.");
        
        int readCount = 0;
        int succCount = 0;
        
        
        Rht03 rht03;
        try {
            rht03 = new Rht03( RaspiPin.GPIO_07 );
        } catch (Rht03Exception ex ) {
            System.out.println( ex.getMessage() );
            return; 
        }

        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
        
        
        Mcp3008 mcp3008 = new Mcp3008(Spi.CHANNEL_0, 1000000);
        int adChannel_0 = 0;
        int adChannel_1 = 1;
        int adChannel_2 = 2;

        int val = 0;
        
        
        ISensorReadingDao sensorDao = SensorReadingDaoFactory.GetDao();

        while (true) {
            
            SensorReadingVO  reading = new SensorReadingVO();
            reading.setLocation("Test hive");
            
            ++readCount;
            try {
                succCount += rht03.read_dht22_dat();
                System.out.println("Read date: " + dt.format(rht03.getLastReadDate()) + 
                        ", Temperature = " + df.format(rht03.getTemperature()) + "*C" + 
                        ", Humidity = " + df.format(rht03.getHumidity()) +" %");
                
                reading.setTemperature(rht03.getTemperature());
                reading.setHumidity(rht03.getHumidity());
                reading.setReadTime(DateHelper.getCurrentDate());
                
            } catch (Rht03Exception ex) {
                System.out.println( ex.getMessage() );
            }
            

            System.out.println( "Success count: " + succCount + " read count: " + readCount + " success rate: " + df.format(((double)succCount/readCount)*100) + "%");
            
            try {
                val = mcp3008.read(adChannel_0);
                reading.setWeight( Double.valueOf(val) );
            } catch (Mcp3008Exception ex) {
                System.out.println( ex );
            }
            
            System.out.println("Data at channel " + adChannel_0 + ": " + val);
            
            try {
                val = mcp3008.read(adChannel_1);
            } catch ( Mcp3008Exception ex) {
                System.out.println( ex );
            }
            
            System.out.println("Data at channel " + adChannel_1 + ": " + val);


            try {
                val = mcp3008.read(adChannel_2);
            } catch ( Mcp3008Exception ex) {
                System.out.println( ex );
            }
            
            System.out.println("Data at channel " + adChannel_2 + ": " + val);
            
            /*
            try {
               sensorDao.insert(reading);
            } catch (ApiaryException ex) {
                log.severe(ex.getMessage());
            }
             */
            System.out.println("____________________________");
            Gpio.delay(2000); // wait 2sec to refresh
        
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        //testDao();
        readSensorsToDb();
    }
    
    
}
