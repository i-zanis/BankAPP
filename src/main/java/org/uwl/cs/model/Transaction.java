package org.uwl.cs.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static org.uwl.cs.Main.currentCustomer;
import static org.uwl.cs.model.Constant.NUMBER_FORMAT;
import static org.uwl.cs.model.Constant.POUND_SYMBOL;
import static org.uwl.cs.model.Database.updateBalance;
import static org.uwl.cs.model.Database.updateOtherCustomerBalance;

/**
 * Class with basic transaction functionalities.
 */
public class Transaction {
    static float interestRate = 3.4f;
    static NumberFormat numberFormat = NumberFormat.getInstance(Locale.UK);
    static DecimalFormat decimalFormat = (DecimalFormat) numberFormat;

    /**
     * Withdraws amount from database and updates the Customer-type object.
     *
     * @param amount
     * @return true if successful
     */
    public static Boolean withdraw(String amount) {
        boolean status = updateBalance(currentCustomer.getAccountNumber(), -Float.parseFloat(amount));
        if (status) {
            currentCustomer.setBalance(currentCustomer.getBalance() - Float.parseFloat(amount));
        }
        return status;
    }

    /**
     * Deposits amount to database and updates the Customer-type object.
     *
     * @param amount
     * @return true if successful
     */
    public static boolean deposit(String amount) {
        boolean status = updateBalance(currentCustomer.getAccountNumber(), Float.parseFloat(amount));
        if (status) {
            currentCustomer.setBalance(currentCustomer.getBalance() + Float.parseFloat(amount));
        }
        return status;
    }

    /**
     * Transfer amount given the right credentials and updates Customer-type object.
     *
     * @param firstName
     * @param lastName
     * @param accountNumber
     * @param amount
     * @return true if successful
     */
    public static Boolean transfer(String firstName, String lastName, String accountNumber, String amount) {
        boolean status = updateBalance(currentCustomer.getAccountNumber(), -Float.parseFloat(amount));
        if (status) {
            updateOtherCustomerBalance(firstName, lastName, accountNumber, Float.parseFloat(amount));
            currentCustomer.setBalance(currentCustomer.getBalance() - Float.parseFloat(amount));
        }
        return status;
    }

    /**
     * Formats the balance and adds the pound symbol. Useful when you need to display the right amount of decimals
     * and indicate that the amount is in pounds.
     *
     * @return current balance formatted
     */
    public static String getUpdatedBalance() {
        decimalFormat.applyPattern(NUMBER_FORMAT);
        return POUND_SYMBOL + decimalFormat.format((currentCustomer.getBalance()));
    }

    /**
     * Calculates the monthly interest from Customer-type object and add the pound symbol.
     *
     * @return monthly interest formatted
     */
    public static String getMonthlyInterest() {
        decimalFormat.applyPattern(NUMBER_FORMAT);
        return POUND_SYMBOL + decimalFormat.format((currentCustomer.getBalance() * interestRate / 100 / 12));
    }

    /**
     * Calculates the annual interest from Customer-type object and add the pound symbol.
     *
     * @return annual interest formatted
     */
    public static String getAnnualInterest() {
        decimalFormat.applyPattern(NUMBER_FORMAT);
        return POUND_SYMBOL + decimalFormat.format((currentCustomer.getBalance() * interestRate / 100));
    }

    /**
     * Calculates the monthly repayment from Customer-type object and add the pound symbol.
     *
     * @return monthly repayment formatted
     */
    public static String getMonthlyLoanRepayment(String amount, String years) {
        decimalFormat.applyPattern(NUMBER_FORMAT);
        int time = Integer.parseInt(years) * 12;
        float rate = (interestRate / 100) / 12;
        return POUND_SYMBOL + decimalFormat.format(((Float.parseFloat(amount) * rate) / (1 - Math.pow(1 + rate,
                (-time)))));
    }
}
