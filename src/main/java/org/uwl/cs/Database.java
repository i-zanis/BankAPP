package org.uwl.cs;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Database {


    public static Connection connect() throws Exception {
        String url = "jdbc:mysql://sql2.freemysqlhosting.net:3306/sql2383785";
        String user = "sql2383785";
        String pwd = "aS2%cM4*";
        Connection con = null;
        con = DriverManager.getConnection(url, user, pwd);
        con.setAutoCommit(false);
        return con;
    }
    public static boolean updateAccount(int accountId, Double newBalance) throws Exception {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            String query = "UPDATE account SET balance = " + newBalance + "WHERE id= " + accountId + ";";
            statement.executeUpdate(query);
            con.commit();
            return true;
            //statement.executeQuery("SELECT * FROM account");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates Relevant information to the Application
     *
     * @return
     */
    public static void updateScreenInformation() {
        getTime();
    }

    public static String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(LocalTime.now().format(dtf));
        return LocalTime.now().format(dtf);
    }

    public static LinkedList<String[]> all() {
        LinkedList<String[]> result = new LinkedList<>();
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account;");
            while (resultSet.next()) {
                String id = Integer.toString(resultSet.getInt("id"));
                String fName = resultSet.getString("first_name");
                String lName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String pwd = resultSet.getString("password");
                String balance = Float.toString(resultSet.getFloat("balance"));
                String phone = resultSet.getString("phone");
                result.add(new String[]{id, fName, lName, email, pwd, balance, phone});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String[] get(int id) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account WHERE id=" + id + ";");
            int columns = resultSet.getMetaData().getColumnCount();
            String[] obj = new String[columns];
            resultSet.next();
            obj[0] = Integer.toString(resultSet.getInt("id"));
            obj[1] = resultSet.getString("first_name");
            obj[2] = resultSet.getString("last_name");
            obj[3] = resultSet.getString("email");
            obj[4] = resultSet.getString("password");
            obj[5] = Float.toString(resultSet.getFloat("balance"));
            obj[6] = resultSet.getString("phone");
            con.close();
            statement.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateBalance(int id, float balance) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT balance FROM account WHERE id=" + id + ";");
            resultSet.next();
            float oldBalance = resultSet.getFloat(1);
            if (balance == 0) {
                return false;
            } else if (balance < 0 || ((oldBalance - balance) < 0)) {
                return false;

            } else {
                float newBalance = oldBalance + balance;
                statement.executeUpdate("UPDATE account SET balance=" + newBalance + " WHERE id=" + id + ";");
                con.commit();
                con.close();
                statement.close();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean create(String fName, String lName, String email, String pwd, String phone) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            pwd = encryptPassword(pwd);
            statement.executeUpdate("INSERT INTO " +
                    "account " +
                    "(first_name, last_name, email, password, phone) " +
                    "Values " +
                    "('" + fName + "', '" + lName + "', '" + email + "', '" + pwd + "', '" + phone + "');"
            );
            con.commit();
            con.close();
            statement.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String pwd = no.toString(16);
            while (pwd.length() < 32) {
                pwd = "0" + pwd;
            }
            return pwd;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static Boolean checkPassword(String email, String password) throws SQLException {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            String pwd = encryptPassword(password);
            ResultSet result = statement.executeQuery(
                    "SELECT password FROM account WHERE email=" + "'" + email.strip() + "';");
            result.next();
            String current_pwd = result.getString("password");
            result.close();
            return current_pwd.equals(pwd);
        } catch (Exception e) {

            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            // System.exit(0);
        }
        return false;
    }

    public static void deleteAll() {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            statement.executeUpdate("DELETE FROM account;");
            con.commit();
            con.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init() {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            String query = "CREATE TABLE account (" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "first_name VARCHAR(50) NOT NULL, " +
                    "last_name VARCHAR(50) NOT nULL, " +
                    "email VARCHAR(250) NOT NULL UNIQUE, " +
                    "password TEXT NOT NULL, " +
                    "balance FLOAT DEFAULT 0.0, " +
                    "phone VARCHAR(11)," +
                    "PRIMARY KEY (id));";
            statement.executeUpdate(query);
            con.commit();
            con.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 