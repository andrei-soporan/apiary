/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.exeption;

/**
 *
 * @author andrei
 */
public class ApiaryException extends Exception {

    /**
     * Creates a new instance of <code>ApiaryException</code> without detail
     * message.
     */
    public ApiaryException() {
    }

    /**
     * Constructs an instance of <code>ApiaryException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ApiaryException(String msg) {
        super(msg);
    }
}
