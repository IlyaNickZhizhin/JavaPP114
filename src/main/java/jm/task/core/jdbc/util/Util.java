package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    protected static final String URL = "Testing";
    static final String USER = "root";
    static final String PASSWORD = "122100";

    public Connection getConnection() {
        Connection connection = null;
        System.out.println("Creating database connection... ");
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection("jdbc:mysql://localhost/"+URL, USER, PASSWORD);
            System.out.println("                                successfully");
        } catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            System.out.println("                                unsuccessfully");
        }
        return connection;
    }
}
