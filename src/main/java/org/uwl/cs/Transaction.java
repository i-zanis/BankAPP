package org.uwl.cs;

import static org.uwl.cs.Database.updateBalance;
import static org.uwl.cs.Main.currentCustomer;

public class Transaction {
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
    public static void deposit(Customer customer, String amount) {
        customer.setBalance(customer.getBalance() + Float.parseFloat(amount));
        updateBalance(currentCustomer.getAccountNumber(),currentCustomer.getBalance());
    }
}
