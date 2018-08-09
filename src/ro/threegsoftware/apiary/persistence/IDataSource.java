/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.persistence;

import java.sql.Connection;

/**
 *
 * @author andrei
 */
public interface IDataSource {

    public Connection getConnection();

}
