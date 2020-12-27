package org.uwl.cs;

public class Transaction {
    /**
     * withdraw method
     * @param customer
     * @param amount
     * @return
     */
    public static Boolean withdraw(Customer customer, String amount) {
            if (customer.getBalance() - Double.parseDouble(amount) > 0.0) return false;
                customer.setBalance(customer.getBalance() - Double.parseDouble(amount));
                return true;
    }
    public static void deposit(Customer customer, String amount) {
        customer.setBalance(customer.getBalance() + Double.parseDouble(amount));
    }
}
