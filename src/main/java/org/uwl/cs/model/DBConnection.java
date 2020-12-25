package org.uwl.cs.model;


import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;

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
    static Connection connection;
    static String url = "jdbc:mysql://localhost/tutorial";

    static String user = "user";
    static String password = "";

     public static Connection connect() {

         //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName, userName, password);
        try {
            System.out.println("Connection to the Database successful.");

            connection = DriverManager.getConnection(url, user, password);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Dialog<String> dialog = new Dialog<>();
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

     static int addAccount(String firstName, String lastName, String email, String telephone, String password) {
        int userId = Integer.MIN_VALUE;
        int accountId = Integer.MIN_VALUE;

        Connection connection = connect();
        try {
            connection.setAutoCommit(false);
            //Add User
            try (PreparedStatement addUser = connection.prepareStatement("INSERT INTO Users(FirstName, LastName, Email, Telephone, Password) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                addUser.setString(1, firstName);
                addUser.setString(2, lastName);
                addUser.setString(3, email);
                addUser.setString(4, telephone);
                addUser.setString(5, password);
                addUser.executeUpdate();
                ResultSet addUserResults = addUser.getGeneratedKeys();
                if (addUserResults.next()) {
                    userId = addUserResults.getInt(1);
                }
            }
            // make new account
            try (PreparedStatement addAccount = connection.prepareStatement("INSERT INTO Accounts(Type, Balance) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS)) {
                addAccount.setString(1, "Current");
                addAccount.setDouble(2, 250.0);
                addAccount.executeUpdate();
                ResultSet addAccountResults = addAccount.getGeneratedKeys();
                if (addAccountResults.next()) {
                    accountId = addAccountResults.getInt(1);
                }
            }
            //link account & user
            if (userId > 0 && accountId > 0) {
                try (PreparedStatement linkAccount = connection.prepareStatement("INSERT INTO Mappings(UserId, AccountId) VALUES(?,?)")) {
                    linkAccount.setInt(1, userId);
                    linkAccount.setInt(2, accountId);
                    linkAccount.executeUpdate();
                }
                connection.commit();
            } else {
                connection.rollback();
            }
            //Disconnect
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountId;
    }


    //Update (Edit Account)
    boolean UpdateAccount(int accountId, Double newBalance){
        boolean success = false;
        Connection connection = connect();
        try {
            try (PreparedStatement updateBalance = connection.prepareStatement(
                    "UPDATE Accounts SET Balance = ? WHERE ID = ?")) {
                updateBalance.setDouble(1, newBalance);
                updateBalance.setInt(2, accountId);
                updateBalance.executeUpdate();
            }
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }


}
