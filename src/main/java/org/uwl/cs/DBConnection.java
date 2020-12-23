package org.uwl.cs;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class helps the user connect to the database.
 * It allows adding new account and updating details.
 */


public class DBConnection {

    String url = "jdbc:mysql://localhost:3306/root";
    String user = "admin";
    String password = "admin";

    private Connection connect() {
        Connection connection;
        System.out.println("Connection to the Database successful.");
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            connection = null;
            System.out.println("Connection to the Database unsuccessful.");
            e.printStackTrace();

        }
        return connection;
    }

    /**
     * Allows to add a new account to the database.
     * @param firstName
     * @param lastName
     * @param
     * @param
     * @param
     * @return
     */

    int addAccount(String firstName, String lastName, String email, String telephone, String password) {
        int userId = Integer.MIN_VALUE;
        int accountId = Integer.MIN_VALUE;

        Connection connection = connect();
        try {
            connection.setAutoCommit(false);
         