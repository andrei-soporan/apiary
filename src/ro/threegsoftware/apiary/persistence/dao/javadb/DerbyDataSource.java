/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.threegsoftware.apiary.persistence.dao.javadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ro.threegsoftware.apiary.persistence.IDataSource;

/**
 *
 * @author andrei
 */
public class DerbyDataSource implements IDataSource {
    
    private final static Logger log = Logger.getLogger( DerbyDataSource.class.getName() );

    private final static String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";

    private final static String SERVER = "//localhost:1527";

    private final static String DATABASE_FILE_PATH = "/NetBeansProjects/apiary/db/apiarydb";
    
    //private final static String DATABASE_FILE_PATH = "//home/pi/NetBeansProjects/apiary/db/ApiaryDb";
    //private final static String DATABASE_FILE_PATH = "/F:/tmp/apiary/db/ApiaryDb";
    
    private final static String USER = "api";

    private final static String PWD = "api";
    
    private static DerbyDataSource _instance = null;
    
    private static Connection _connection = null;

    public static DerbyDataSource getInstance() throws SQLException {
        if (_instance == null) {
            _instance = new DerbyDataSource();
        }
        return _instance;
    }
    
    protected DerbyDataSource() throws SQLException {
        _connection = initConnection();
    }
    
    @Override
    public Connection getConnection() {
        return _connection;
    }

    private static Connection initConnection() throws SQLException  {
        Connection conn;
        String connectionString = "jdbc:derby:" + SERVER + DATABASE_FILE_PATH;

        log.info("Connecting to Derby database: " + SERVER + DATABASE_FILE_PATH);
        
        try {
            Class.forName( JDBC_DRIVER);
        } catch ( ClassNotFoundException ex ) {
            throw new SQLException(JDBC_DRIVER + " class can not be found!");
        } 
        conn =DriverManager.getConnection( connectionString, "api", "api" );
        conn.setAutoCommit(true);

        return conn;

/*     try {
            //conn =DriverManager.getConnection("jdbc:derby://localhost:1527//f://tmp//apiary//db//ApiaryDb", "api", "api" );
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DerbyDataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
  */      
        /*
        String strDbServer = ProjectSettings.getInstance().getDbServer();
        String strDbPath = ProjectSettings.getInstance().getDbPath();

        System.out.println("Trying to connect to " + JDBC_DRIVER + ":" + strDbServer + ":" + strDbPath + "...\n");
        
        try {
        	Class.forName ("org.firebirdsql.jdbc.FBDriver");
            connection = DriverManager.getConnection(JDBC_DRIVER + ":" + strDbServer + ":" + strDbPath, USER, PWD);
        }
        catch (SQLException ex) {
            _log.error("initConnection --> Unable to acquire connection to " + JDBC_DRIVER + ":" + strDbServer
                       + ":" + strDbPath + ", " + USER, ex);
        }
        catch (ClassNotFoundException ex) {
            _log.error("SQL Driver", ex);
        }

	try {
            //_connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	    connection.setAutoCommit(false);
	}
	catch (SQLException ex) {
            _log.error("Unable to set the autoCommit to false in method initConnection!", ex);
	}

        return connection;
        */
    }
}
