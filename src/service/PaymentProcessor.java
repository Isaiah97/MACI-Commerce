package service;

import model.*;


/**
 * TODO: Implement payment logic
 */

public class PaymentProcessor {
    public boolean processPayment(String cardNumber, String expiry, String cvv, double amount) {
        return cardNumber.startsWith("4") && amount > 0;
    }
}

