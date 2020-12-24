package org.uwl.cs;

import java.io.Serializable;


public abstract class Account implements Serializable{
    private double balance = 0;
    private int accountNumber;
    
    Account(int accountNumber){
        this.accountNumber = accountNumber;
    }
      
