package org.uwl.cs.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Database class with methods to manipulate the information. There are some methods such as deleteUser()/dropTable() for
 * testing and future implementations.
 */
public class Database {

    /**
     * Connects to the database. First method to check when there is a connection problem in the database.
     *
     * @return Connection object
     * @throws Exception
     */
    public static Connection connect() throws Exception {
        String url = "jdbc:mysql://byfcpp9vrgzbrtqotooc-mysql.services.clever-cloud.com:3306/byfcpp9vrgzbrtqotooc";
        String user = "uupoc6dwtojsvpfm";
        String pwd = "ZglEpADBDMfdw7g28KLu";
        Connection con = null;
        con = DriverManager.getConnection(url, user, pwd);
        con.setAutoCommit(false);
        return con;
    }

    /**
     * Creates a customer object by the id(account number) of the user.
     *
     * @param id
     * @return Customer object
     */
    public static Customer getCustomerById(String id) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account WHERE id=" + Integer.parseInt(id) + ";");
            resultSet.next();
            Customer customer = new Customer(
                    resultSet.getInt("id") + "",
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    Float.parseFloat(String.valueOf(resultSet.getFloat("balance"))),
                    resultSet.getString("phone"));
            con.close();
            statement.close();
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates account information by the id(account number) and updates to the new balance.
     *
     * @param accountId  account number
     * @param newBalance
     * @return boolean
     */
    public static boolean updateAccount(int accountId, Double newBalance) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            String query = "UPDATE account SET balance = " + newBalance + "WHERE id= " + accountId + ";";
            statement.executeUpdate(query);
            con.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Displays the existing customers in the database. Currently used for testing by running the Database Main method.
     *
     * @return existing customers in a LinkedList
     */
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

    /**
     * Gets the customers details by email
     *
     * @param email
     * @return Customer Object
     */
    public static Customer getCustomerByEmail(String email) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account WHERE email='" + email + "';");
            resultSet.next();
            Customer customer = new Customer(
                    resultSet.getInt("id") + "",
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    Float.parseFloat(String.valueOf(resultSet.getFloat("balance"))),
                    resultSet.getString("phone"));
            con.close();
            statement.close();
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Adds a specified bonus for new accounts.
     *
     * @param email   Customers email
     * @param balance Desired bonus balance
     */
    public static void addRegistrationBonus(String email, float balance) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT balance FROM account WHERE email='" + email + "';");
            resultSet.next();
            float oldBalance = resultSet.getFloat(1);
            float newBalance = oldBalance + balance;
            statement.executeUpdate("UPDATE account SET balance=" + newBalance + " WHERE email='" + email + "';");
            con.commit();
            con.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates the balance of the customer given correct id.
     *
     * @param id      account number
     * @param balance desired balance
     * @return true on successful update
     */
    public static boolean updateBalance(String id, float balance) {
        boolean status = false;
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT balance FROM account WHERE id=" + id + ";");
            resultSet.next();
            float oldBalance = resultSet.getFloat(1);
            if (balance == 0) {
                status = false;
            } else if (balance < 0) {
                status = !(oldBalance + balance < 0);
            }

            if (status || balance > 0) {
                float newBalance = oldBalance + balance;
                statement.executeUpdate("UPDATE account SET balance=" + newBalance + " WHERE id=" + id + ";");
                con.commit();
                con.close();
                statement.close();
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return status;
        }
    }

    /**
     * Updates the balance of a different customer. Used on the [Money Transfer] feature of the application that the user
     * has to input the correct credentials to transfer money to another customer.
     *
     * @param firstName     Target customer (can be self)
     * @param lastName      Target customer (can be self)
     * @param accountNumber Target customer
     * @param balance       Amount to transfer
     * @return true on successful transfer
     */
    public static boolean updateOtherCustomerBalance(String firstName, String lastName, String accountNumber, float balance) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT balance FROM account WHERE first_name='" + firstName + "' AND last_name='" + lastName + "' AND id='" + accountNumber + "';");
            resultSet.next();
            float oldBalance = resultSet.getFloat(1);
            float newBalance = oldBalance + balance;
            statement.executeUpdate("UPDATE account SET balance='" + newBalance + "' WHERE first_name='" + firstName + "' AND last_name='" + lastName + "' AND id='" + accountNumber + "';");
            con.commit();
            con.close();
            statement.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Confirms customer exists or creates a new one.
     *
     * @param fName
     * @param lName
     * @param email
     * @param pwd
     * @param phone
     * @return true on successful creation/false if user exists
     */
    public static boolean getOrCreateUser(String fName, String lName, String email, String pwd, String phone) {
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

    /**
     * Encrypts the user's password
     *
     * @param password
     * @return
     */
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

    /**
     * Validates if email has the correct format.
     *
     * @param email
     * @return true on correct email format
     */
    public static boolean validateEmail(String email) {
        String rePattern = "^\\w+@(\\w+.[a-zA-Z]+|\\w+.[a-zA-Z]+.[a-zA-Z]+)$";
        Pattern pattern = Pattern.compile(rePattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email.strip());
        return matcher.find();
    }

    /**
     * Valids if phone number has the correct format.
     *
     * @param phoneNumber
     * @return true on correct number format
     */
    public static boolean validatePhoneNumber(String phoneNumber) {
        String rePattern = "^(?:\\+\\d{1,3}|0{0,2})\\d{8,12}$";
        Pattern pattern = Pattern.compile(rePattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phoneNumber.strip());
        return matcher.find();
    }

    /**
     * Password validation of [Minimum eight characters, at least one letter and one number] format
     *
     * @param password
     * @return true on correct password format
     */
    public static boolean validatePassword(String password) {
        String rePattern = "^(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        Pattern pattern = Pattern.compile(rePattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    /**
     * Checks if the credentials provided match the database's.
     *
     * @param email
     * @param password
     * @return true when correct credentials
     */
    public static Boolean checkPassword(String email, String password) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            String pwd = encryptPassword(password);
            ResultSet result = statement.executeQuery(
                    "SELECT password FROM account WHERE email=" + "'" + email.strip() + "';"
            );
            if (result.next()) {
                String current_pwd = result.getString("password");
                con.close();
                statement.close();
                return current_pwd.equals(pwd);
            }
            con.close();
            statement.close();
            result.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes all customers in the database. Useful for testing.
     */
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

    /**
     * Create a new table in the database.
     */
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
                    "phone VARCHAR(15)," +
                    "PRIMARY KEY (id));";
            statement.executeUpdate(query);
            con.commit();
            con.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete the table in the database.
     */
    public static void dropTable() {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            statement.executeUpdate("DROP TABLE account;");
            con.commit();
            con.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a customer given the right id.
     *
     * @param id account number
     */
    public static void deleteUser(int id) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            statement.executeUpdate("DELETE FROM account WHERE id=" + id + ";");
            con.commit();
            con.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a customer given the right email.
     *
     * @param email
     */
    public static void deleteUser(String email) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            statement.executeUpdate("DELETE FROM account WHERE email='" + email + "';");
            con.commit();
            con.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks for correct credentials and allows user to login to the database.
     *
     * @param email
     * @param pwd
     * @return true on correct credentials
     */
    public static boolean login(String email, String pwd) {
        try {
            if (checkPassword(email, pwd)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     * Checks if customer exists.
     *
     * @param email
     * @param pwd
     * @return true on target customer existing
     */
    public static int existsID(String email, String pwd) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();

            String query = "SELECT * FROM account WHERE email='" + email + "' AND password='" + encryptPassword(pwd) + "';";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

            return -1;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return -1;
    }

    /**
     * Checks if email exists
     *
     * @param email
     * @return true on target email existing
     */
    public static Boolean existsEmail(String email) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            String query = "SELECT * FROM account WHERE email='" + email + "';";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                resultSet.getString("email");
                System.err.println("The email provided already exists.");
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if customer exists given the right credentials. Used in [Money Transfer] functionality of the application.
     *
     * @param firstName     target customer first name
     * @param lastName      target customer last name
     * @param accountNumber target customer account number
     * @return true if target customer exists
     */
    public static Boolean existsCustomer(String firstName, String lastName, String accountNumber) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            String query = "SELECT * FROM account WHERE first_name='" + firstName + "' AND  last_name='" + lastName + "' AND id='" + accountNumber + "';";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                resultSet.getString("first_name");
                resultSet.getString("last_name");
                resultSet.getString("id");
                System.err.println("The customer exists.");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("The customer doesn't exist.");
        return false;
    }

    /*
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
           public static void makeQuery(String query) {
        try {
            Connection con = connect();
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
            con.commit();
            con.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
     */


    public static void main(String[] args) {
        //getOrCreateUser("Markiur", "Namher", "test@test.com", "testing", "07717091689");
        //updateOtherCustomerBalance("Sea", "Chan", "12365495", 50);
        //existsCustomer("Sea", "Chan",  "12365495");
        //deleteAll();
        //System.out.println(getOrCreateUser("Markiur", "Namher", "test@test.com", "testing", "07717091689"));
        //makeQuery("ALTER TABLE account AUTO_INCREMENT=12354898");
        for (String[] a : all()) {
            System.out.println(Arrays.toString(a));
        }
    }
}
