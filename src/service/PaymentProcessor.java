package service;

import model.*;

public class PaymentProcessor {
    public boolean processPayment(String cardNumber, String expiry, String cvv, Order order) {
        boolean success = cardNumber.startsWith("4") && order.getTotal() > 0;
        order.setStatus(success ? OrderStatus.PAID : OrderStatus.FAILED);
        return success;
    }
}
