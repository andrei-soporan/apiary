/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.persistence.dao;

import ro.threegsoftware.apiary.persistence.dao.javadb.SensorReadingDerbyDao;

/**
 *
 * @author andrei
 */
public class SensorReadingDaoFactory {
    
    //private SensorReadingDaoFactory {
    //}
    
    public static ISensorReadingDao GetDao() {
        return new SensorReadingDerbyDao();
    }
    
}
