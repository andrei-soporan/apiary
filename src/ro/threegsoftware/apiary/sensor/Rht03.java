/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.sensor;

import com.pi4j.component.temperature.TemperatureSensorBase;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.temperature.TemperatureScale;
import com.pi4j.wiringpi.Gpio;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import ro.threegsoftware.apiary.sensor.exception.Rht03Exception;

/**
 *
 * @author andrei
 */
public class Rht03 extends TemperatureSensorBase {
 
    private final Pin RHT_PIN;
    
    private double lastHumidity = 0.0;
    
    private double lastTemperature = 0.0;    
    
    private long lastReadTime = 0;
    
    private final int[] dht22_dat;
    
    public Rht03( Pin dataPin ) throws Rht03Exception {
        this.RHT_PIN = dataPin;
        initGpio();
        dht22_dat = new int[5];
    }
    
    @Override
    public double getTemperature() {
        return lastTemperature;
    }

    @Override
    public TemperatureScale getScale() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Double getHumidity() {
        return lastHumidity;
    }

    public Date getLastReadDate() throws Rht03Exception {
        read_dht22_dat();
        
        Calendar gc = GregorianCalendar.getInstance();
        gc.setTimeInMillis(lastReadTime);
        
        return gc.getTime();
    }
    
    private void initGpio() throws Rht03Exception {
        if (Gpio.wiringPiSetup() == -1)
           throw new Rht03Exception("Error while setting up wiringPi.");
    }
    
    static int sizecvt(int read) throws Rht03Exception {
	/* digitalRead() and friends from wiringpi are defined as returning a value
	< 256. However, they are returned as int() types. This is a safety function */

	if (read > 255 || read < 0)
	{
		//printf("Invalid data from wiringPi library\n");
            throw new Rht03Exception("Invalid read value!");
	}
	return read;
    }

    
    public int read_dht22_dat() throws Rht03Exception 
    {
	int laststate = Gpio.HIGH;
	int counter;
	int j = 0, i;

        dht22_dat[0] = dht22_dat[1] = dht22_dat[2] = dht22_dat[3]  = dht22_dat[4] = 0;

        int MAXTIMINGS = 85;

	// pull pin down for 18 milliseconds
	Gpio.pinMode(RHT_PIN.getAddress(), Gpio.OUTPUT);
	Gpio.digitalWrite(RHT_PIN.getAddress(), Gpio.HIGH);
	Gpio.delay(10);
	Gpio.digitalWrite(RHT_PIN.getAddress(), Gpio.LOW);
	Gpio.delay(18);
	// then pull it up for 40 microseconds
	Gpio.digitalWrite(RHT_PIN.getAddress(), Gpio.HIGH);
	Gpio.delayMicroseconds(40);
	// prepare to read the pin
	Gpio.pinMode(RHT_PIN.getAddress(), Gpio.INPUT);

	// detect change and read data
	for ( i=0; i< MAXTIMINGS; i++) {
            counter = 0;
            while ( sizecvt(Gpio.digitalRead(RHT_PIN.getAddress())) == laststate ) {
                counter++;
		Gpio.delayMicroseconds(1);
                if (counter == 255) {
                    break;
		}
            }
            laststate = sizecvt( Gpio.digitalRead(RHT_PIN.getAddress()) );

            if (counter == 255) break;

            // ignore first 3 transitions
            if ((i >= 4) && (i%2 == 0)) {
                // shove each bit into the storage bytes
		dht22_dat[j/8] <<= 1;
		if (counter > 16)
                    dht22_dat[j/8] |= 1;
                    j++;
            }
	}

	// check we read 40 bits (8bit x 5 ) + verify checksum in the last byte
	// print it out if data is good
	if ( j >= 40 ) {
            if ( (dht22_dat[4] == ((dht22_dat[0] + dht22_dat[1] + dht22_dat[2] + dht22_dat[3]) & 0xFF)) ) {
                double t, h;
		h = (double)dht22_dat[0] * 256 + (double)dht22_dat[1];
		h /= 10;
		t = (double)(dht22_dat[2] & 0x7F)* 256 + (double)dht22_dat[3];
		t /= 10.0;
		
                if ((dht22_dat[2] & 0x80) != 0) t *= -1;
                        
                lastTemperature = t;
                lastHumidity = h;
                lastReadTime = GregorianCalendar.getInstance().getTime().getTime();
                        
                return 1;
            } else {
                throw new Rht03Exception( "Checksum error while reading data from sensor." );
                    //+ " Calculated checksum is " + calculatedChecksum
                    //+ " sensor checksum is " + rawChecksum );
            }
	}
	else
	{
            System.out.println("Data not good, skip.");
            return 0;
	}
    }

    public static void main(String[] args) throws InterruptedException {

        System.out.println("<--Pi4J--> RHT03 relative humidity and temperature sensor ... started.");
        
        int readCount = 0;
        int succCount = 0;
        

        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 

        Rht03 rht03;
        try {
            rht03 = new Rht03( RaspiPin.GPIO_07 );
        } catch (Rht03Exception ex ) {
            System.out.println( ex.getMessage() );
            return; 
        }

        while ( true )
	{
            ++readCount;
            try {
                succCount += rht03.read_dht22_dat();
                System.out.println("Read date: " + dt.format(rht03.getLastReadDate()) + 
                        ", Temperature = " + df.format(rht03.getTemperature()) + "*C" + 
                        ", Humidity = " + df.format(rht03.getHumidity()) +" %");
            } catch (Rht03Exception ex) {
                System.out.println( ex.getMessage() );
            }
            
            System.out.println( "Success count: " + succCount + " read count: " + readCount + " success rate: " + df.format(((double)succCount/readCount)*100) + "%");
            Gpio.delay(2000); // wait 1sec to refresh
	}
    }    

        
}
