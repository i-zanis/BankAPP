package org.uwl.cs;

public class Customer {
    int accountNumber;
    String firstName;
    String lastName;
    String email;
    String password;
    float balance;
    String phone;


    // Constructor method to save the data to reduce the calls to the database
    public Customer(int accountNumber, String firstName, String lastName, String email, String password, float balance, String phone) {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.phone = phone;
    }

    // ***** Accessor methods *****
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public float getBalance() {
        return balance;
    }

    public String getPhone() {
        return phone;
    }
    // ***** End of Accessor methods *****

    // ***** Mutator Methods *****
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    // ***** End of Mutator methods *****
}


