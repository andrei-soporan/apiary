/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.sensor.exception;

/**
 *
 * @author andrei
 */
public class Rht03Exception extends Exception {

    /**
     * Creates a new instance of <code>Rht03Exception</code> without detail
     * message.
     */
    public Rht03Exception() {
    }

    /**
     * Constructs an instance of <code>Rht03Exception</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public Rht03Exception(String msg) {
        super(msg);
    }
}
