/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.persistence;

import java.sql.SQLException;
import ro.threegsoftware.apiary.persistence.dao.javadb.DerbyDataSource;

/**
 *
 * @author andrei
 */
public class DataSourceFactory {
 
    protected DataSourceFactory() {
    }

    public static IDataSource getDataSource() throws SQLException {
        return DerbyDataSource.getInstance();
    }
}
