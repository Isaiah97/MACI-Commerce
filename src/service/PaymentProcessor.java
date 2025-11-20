package service;

public class PaymentProcessor {
    public boolean processPayment(String cardNumber, String expiry, String cvv, double amount) {
        return cardNumber.startsWith("4") && amount > 0;
    }
}
