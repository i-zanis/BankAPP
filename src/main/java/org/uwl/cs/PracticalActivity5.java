package org.uwl.cs;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/*

public class PracticalActivity5 {
    private int id = 0;
    private double balance = 0;
    private double annualInterestRate = 0;
    private final Date dateCreated = new Date();


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
            System.out.println("\t- You do not have enough money!");
        } else {
            this.balance -= amount;
            System.out.println("\t- You have withdrawn £" + amount + ".");
        }
    }

    public void deposit(double amount) {
        this.balance += amount;
        System.out.println("\t- You have deposited £" + amount + ".");
    }

    public void applyAnnualInterestRate() {
        this.balance += balance * (annualInterestRate / 100);
    }

    public Account(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account() {
    }


}

class MainScene {
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(">>> Creating account...");
        Account account = new Account(1122, 20000);

        sleep(1000);

        System.out.println(">>> Setting annual interest rate to 4.5%...");
        account.setAnnualInterestRate(4.5);

        sleep(1000);


        System.out.println(">>> Applying annual interest rate...");
        account.applyAnnualInterestRate();

        sleep(1000);

        System.out.println(">>> Withdrawing money...");
        account.withdraw(2500);

        sleep(1000);


        System.out.println(">>> Depositing money...");
        account.withdraw(3000);

        sleep(1000);

        System.out.println(">>> Fetching Data...");
        DecimalFormat numberFormatter = new DecimalFormat("#0.00");
        System.out.println("\t- Your balance is: £" + numberFormatter.format(account.getBalance()) + ".");

        double monthlyInterest = account.getBalance() * account.getMonthlyInterestRate() / 100;
        System.out.println("\t- Your monthly interest is: £" + numberFormatter.format(monthlyInterest) + ".");

        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss, yyy/MM/dd");
        System.out.println("\t- Account creation date: " + dateFormatter.format(account.getDateCreated()) + ".");
    }
}


 */