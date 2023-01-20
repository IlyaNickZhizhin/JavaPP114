package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public class Util{

/**
 Minimum required fields for creating connection
 */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "Testing";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

/**
 just Logger
 */
    private static Logger logger = Logger.getLogger("Util log");

/**
 Field and constructor for singleton realization
*/
    private static volatile Util util;

/**
Pool of connections creating
*/
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> givenAwayConnection;
    static final int POOL_SIZE = 4;
/**
 Fields for hibernate
 */


    /**
 Getter for making field URL private
 */
    public String getURL() {
        return URL;
    }

    private Util(){
        freeConnections = new LinkedBlockingDeque<>();
        givenAwayConnection = new LinkedList<>();
        try {
            for (int i = 0; i<POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/"+URL, USER, PASSWORD);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.add(proxyConnection);
                logger.info("Connection #" + (i+1) + " was added to pool");
            }
            logger.info("Connection pool from " + POOL_SIZE + " connections was created");
        } catch (SQLException e) {
            logger.severe("Initialization was unsuccessfully" + e.getMessage());
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            logger.info("Connection was given to UtilService");
            givenAwayConnection.add(connection);
        } catch (InterruptedException e){
            e.printStackTrace();
            logger.warning("Connection was NOT given to UtilService");
        }
        return connection;
    }
    public void closeConnection(Connection connection) {
            if (connection instanceof ProxyConnection) {
                givenAwayConnection.remove(connection);
                freeConnections.add((ProxyConnection) connection);
            } else {
                logger.warning("ProxyConnection was NOT closed");
            }
    }



    public static Util getUtil(){
        Util localUtil = util;
        if (localUtil == null) {
            synchronized (Util.class) {
                localUtil = util;
                if (localUtil == null) {
                    util = localUtil = new Util();
                }
            }
        }
        return util;
    }
}
