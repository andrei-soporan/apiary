/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.extension.exception;

/**
 *
 * @author andrei
 */
public class Mcp3008Exception extends Exception {
    /**
     * Creates a new instance of <code>Mcp3008Exception</code> without detail
     * message.
     */
    public Mcp3008Exception() {
    }

    /**
     * Constructs an instance of <code>Mcp3008Exception</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public Mcp3008Exception(String msg) {
        super(msg);
    }
    
}
