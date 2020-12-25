package org.uwl.cs;

import java.sql.*;

public class Database {
    public static Connection connect() throws Exception {
        //  String url = "jdbc:mysql://localhost:3306/mysql";
        String url = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2383785";
        String user = "sql2383785";
        String pwd = "aS2%cM4*";
//        Class.forName("com.mysql.jdbc.Driver");
        Connection con = null;
        con = DriverManager.getConnection(url, user, pwd);
        con.setAutoCommit(false);
        System.out.println("connected");
        return con;
    }


    public static boolean updateAccount(int accountId, Double newBalance) throws Exception {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            String query = "UPDATE account SET balance = "+ newBalance + "WHERE id=2;";
            statement.executeUpdate(query);
            con.commit();
            return true;
            //statement.executeQuery("SELECT * FROM account");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
        }
    }
