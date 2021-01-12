package org.uwl.cs;

import java.util.Date;


public class Account {

    private final Date dateCreated = new Date();
    private int id = 0;
    private double balance = 0;
    private double annualInterestRate = 0;

    Account() {
    }

    Account(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public static void main(String[] args) {
        Account account1 = new Account(1122, 20_000);
        System.out.println(account1.balance);
        account1.setAnnualInterestRate(4.5);
        account1.withdraw(2500);
        account1.deposit(3000);
        System.out.println(account1.balance);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAnnualInterestRate() {
        return this.annualInterestRate;
    }

    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public double getMonthlyInterestRate() {
        return this.annualInterestRate / 12;
    }

    public void withdraw(double amount) {
        if (this.balance < amount) {
            System.out.println("Insufficient funds.");
        } else {
            this.balance -= amount;
            System.out.println("£" + amount + " has been withdrawn.");
        }
    }

    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("£" + amount + " has been deposited.");
    }

    public void applyAnnualInterestRate() {
        this.balance += balance * (annualInterestRate / 100);
    }
}



