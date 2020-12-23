package org.uwl.cs;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    public Connection connection;

    public Connection initializeDB() {


        String dbName = "tutorial";
        String userName = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("The Driver has been loaded.");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, userName, password);
            System.out.println("Connection to the Database successful.");


        } catch (Exception e) {
            System.out.println("Connection to the Database unsuccessful.");
            e.printStackTrace();
        }


        return connection;
    }
}
