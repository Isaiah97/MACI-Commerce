package app;

import model.*;
import service.*;
import java.util.*;

public class DeliveryMain {
    public static void main(String[] args) {

        //  SAMPLE DATA
        Bouquet b1 = new Bouquet("B001", "Rose Delight", "Roses", 29.99, true);
        Bouquet b2 = new Bouquet("B002", "Tulip Charm", "Tulips", 19.99, true);

        Order order = new Order(List.of(b1, b2), ShippingMethod.EXPRESS);
        OrderService orderService = new OrderService();
        orderService.addOrder(order);

        //  PAYMENT PROCESS
        sectionHeader("PAYMENT PROCESSING");

        PaymentProcessor payment = new PaymentProcessor();
        double totalAmount = order.getTotal();
        boolean paid = payment.processPayment("4111111111111111", "12/26", "123", totalAmount);

        if (paid) {
            System.out.println("✔ Payment approved for Order ID: " + order.getOrderId());
            EmailService email = new EmailService();
            email.sendConfirmation(order);
        } else {
            System.out.println("❌ Payment failed for Order ID: " + order.getOrderId());
        }

        //  DELIVERY STATUS
        sectionHeader("DELIVERY STATUS");

        DeliveryTracker tracker = new DeliveryTracker();
        tracker.updateDeliveryStatus(order);
        tracker.printTrackingInfo(order);
        tracker.markAsDelivered(order);

        //  ADMIN VIEW
        sectionHeader("INCOMING ORDERS");

        for (Order o : orderService.getAllOrders()) {
            printOrderSummary(o);
            System.out.println("----------------------------------------");
        }
    }

    //         Helper Formatting

    private static void sectionHeader(String title) {
        System.out.println("\n========================================");
        System.out.println("           " + title);
        System.out.println("========================================");
    }

    private static void printOrderSummary(Order order) {
        System.out.printf("Order ID: %s%n", order.getOrderId());
        System.out.println("Items:");
        for (Bouquet b : order.getItems()) {
            System.out.printf("  - %-30s $%-6.2f [%s]%n",
                    b.getName(), b.getPrice(), b.isAvailable() ? "Available" : "Unavailable");
        }
        System.out.printf("Shipping: %-15s%n", order.getShippingMethod());
        System.out.printf("Total: $%.2f%n", order.getTotal());
        System.out.printf("Status: %s%n", order.getStatus());
        System.out.printf("Delivery Status: %s%n", order.getDeliveryStatus());
    }
}
