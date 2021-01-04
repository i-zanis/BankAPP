package org.uwl.cs.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static org.uwl.cs.Main.currentCustomer;
import static org.uwl.cs.model.Constant.NUMBER_FORMAT;
import static org.uwl.cs.model.Constant.POUND_SYMBOL;
import static org.uwl.cs.model.Database.getCustomerByEmail;
import static org.uwl.cs.model.Database.updateBalance;

public class Transaction {
    static float interestRate = 3.4f;
    static NumberFormat numberFormat = NumberFormat.getInstance(Locale.UK);
    static DecimalFormat decimalFormat = (DecimalFormat) numberFormat;

    /**
     * withdraw method
     *
     * @param amount
     * @return
     */
    public static Boolean withdraw(String amount) {
        boolean status = updateBalance(currentCustomer.getAccountNumber(), - Float.parseFloat(amount));
        if (status) {
            currentCustomer.setBalance(currentCustomer.getBalance() - Float.parseFloat(amount));
        }
        return status;
    }

    public static boolean deposit(String amount) {
        boolean status = updateBalance(currentCustomer.getAccountNumber(), Float.parseFloat(amount));
        if (status) {
            currentCustomer.setBalance(currentCustomer.getBalance() + Float.parseFloat(amount));
        }
        return status;
    }

    public static String getUpdatedBalance() {
        decimalFormat.applyPattern(NUMBER_FORMAT);
        return POUND_SYMBOL + decimalFormat.format((currentCustomer.getBalance()));
    }

    public static String getMonthlyInterest() {
        decimalFormat.applyPattern(NUMBER_FORMAT);
        return POUND_SYMBOL + decimalFormat.format((currentCustomer.getBalance() * interestRate / 100 / 12));
    }

    public static String getAnnualInterest() {
        decimalFormat.applyPattern(NUMBER_FORMAT);
        return POUND_SYMBOL + decimalFormat.format((currentCustomer.getBalance() * interestRate / 100));
    }

    public static String getMonthlyLoanRepayment(String amount, String years) {
        decimalFormat.applyPattern(NUMBER_FORMAT);
        int time = Integer.parseInt(years) * 12;
        float rate = (interestRate / 100) / 12;
        return POUND_SYMBOL + decimalFormat.format(((Float.parseFloat(amount) * rate) / (1 - Math.pow(1 + rate,
                (-time)))));
    }
}