package app;

import model.*;
import service.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Sample bouquets
        Bouquet b1 = new Bouquet("B001", "Rose Delight", "Roses", 29.99, true);
        Bouquet b2 = new Bouquet("B002", "Tulip Charm", "Tulips", 19.99, true);

        // Create order
        Order order = new Order(List.of(b1, b2), ShippingMethod.EXPRESS);
        OrderService orderService = new OrderService();
        orderService.addOrder(order);

        // Process payment
        PaymentProcessor payment = new PaymentProcessor();
        boolean paid = payment.processPayment("4111111111111111", "12/26", "123", order);

        // Send email if successful
        if (paid) {
            EmailService email = new EmailService();
            email.sendConfirmation("customer@example.com", order);
        } else {
            System.out.println("❌ Payment failed for Order ID: " + order.getOrderId());
        }

        // Track delivery
        DeliveryTracker tracker = new DeliveryTracker();
        tracker.updateDeliveryStatus(order);
        tracker.markAsDelivered(order);

        // Admin views all orders
        System.out.println("\n📋 Incoming Orders:");
        orderService.getAllOrders().forEach(System.out::println);
    }
}

