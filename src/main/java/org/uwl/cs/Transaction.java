package org.uwl.cs;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static org.uwl.cs.Database.updateBalance;
import static org.uwl.cs.Main.currentCustomer;
import static org.uwl.cs.Util.EMPTY_STRING;
import static org.uwl.cs.Util.POUND_SYMBOL;

public class Transaction {
    static float interestRate = 3.4f;
    /**
     * withdraw method
     * @param amount
     * @return
     */
    public static Boolean withdraw(String amount) {
            if (Double.parseDouble(amount) > currentCustomer.getBalance()) return false;
            currentCustomer.setBalance(currentCustomer.getBalance() - Float.parseFloat(amount));
            updateBalance(currentCustomer.getAccountNumber(),currentCustomer.getBalance());
            return true;
    }
    public static boolean deposit(String amount) {
       currentCustomer.setBalance(currentCustomer.getBalance() + Float.parseFloat(amount));
        updateBalance(currentCustomer.getAccountNumber(),currentCustomer.getBalance());
        return true;
    }
    public static String getMonthlyInterest() {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.UK);
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        decimalFormat.applyPattern("00.0##");
        return POUND_SYMBOL + decimalFormat.format((currentCustomer.getBalance() * interestRate/12));
    }
    public static String getAnnualInterest() {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.UK);
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        decimalFormat.applyPattern("00.0##");
        return POUND_SYMBOL + decimalFormat.format((currentCustomer.getBalance() * interestRate));
    }
}
