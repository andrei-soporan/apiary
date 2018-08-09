/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.threegsoftware.apiary.extension;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.Spi;
import ro.threegsoftware.apiary.extension.exception.Mcp3008Exception;

/**
 *
 * @author andrei
 */
public class Mcp3008 {

    private final int SPI_CHANNEL;

    private final int SPEED;

    private final static int START_BYTE = 1;
    
    private final static int END_BYTE = 0;
    
    private final static int COMMAND_LEN = 3;
    
    private final byte[] command_buffer;


    /**
     * <p> This is the constructor of the class.</p>
     * <p>
     * <b>NOTE:.</b>
     * </p>
     * @param channel represents the analog input channel (0-7)
     * 
     * @param speed is the speed of the SPI buss in Hz (i.e. 1000000 represents 1Mhz)
     */
    public Mcp3008(int channel, int speed) {
        this.command_buffer = new byte[COMMAND_LEN];
        this.SPI_CHANNEL = channel;
        this.SPEED = speed;

        Spi.wiringPiSPISetup(this.SPI_CHANNEL, SPEED);
    }
    
    public int read(int adChannel) throws Mcp3008Exception {
        int val = 0;

        // first byte transmitted -> start bit
        command_buffer[0] = START_BYTE;  
        
        // second byte transmitted -> (SGL/DIF = 1, D2=D1=D0=0)
        command_buffer[1] = (byte)( 128 | ((adChannel & 7) << 4) ); 
        
        // third byte transmitted....don't care
        command_buffer[2] = END_BYTE; 
        
        if ( Spi.wiringPiSPIDataRW( SPI_CHANNEL, command_buffer, COMMAND_LEN ) == -1 ) {
            throw new Mcp3008Exception("Error readin data from MCP3008 on analog pin " + adChannel);
        }

        //merge command_buffer[1] & command_buffer[2] to get the 10 bits of the result
        val = (command_buffer[1] << 8) & 0x300; 
        val |= (command_buffer[2] & 0xff);

        return val;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("<--Pi4J--> Reading from MCP3008 IC ... started.");
        
        Mcp3008 mcp3008 = new Mcp3008(Spi.CHANNEL_0, 1000000);
        int adChannel_0 = 0;
        int adChannel_1 = 1;
        int adChannel_2 = 2;

        int val = 0;
        
        while (true) {
            try {
                val = mcp3008.read(adChannel_0);
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
            
            System.out.println("____________________________");
            
            Gpio.delay(2000); // wait 2sec to refresh
        }
    }

}
