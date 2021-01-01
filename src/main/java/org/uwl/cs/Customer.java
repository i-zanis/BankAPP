package org.uwl.cs;

public class Customer {
    String accountNumber;
    String firstName;
    String lastName;
    String email;
    String password;
    float balance;
    String phone;


    // Constructor method to save the data to reduce the calls to the database
    public Customer(String accountNumber, String firstName, String lastName, String email, String password, float balance, String phone) {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.phone = phone;
    }

    // ***** Accessor methods *****
    public String getAccountNumber() {
        return accountNumber;
    }

    // ***** Mutator Methods *****
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    // ***** End of Accessor methods *****

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    // ***** End of Mutator methods *****
}


