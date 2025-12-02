package app;

import model.Bouquet;
import model.Order;
import model.ShippingMethod;
import service.CatalogService;
import service.PaymentProcessor;
import service.EmailService;
import java.util.*;

public class ProductCatalogMain {
    public static void main(String[] args) {
        CatalogService catalog = new CatalogService();
        List<Bouquet> results = catalog.search("Rose", "Roses", 10, 50);
        results.forEach(System.out::println);
        dashboard.displayCatalog(catalog.getAll());

        Order order = new Order(results, ShippingMethod.EXPRESS);
        PaymentProcessor payment = new PaymentProcessor();
        boolean success = payment.processPayment("4111111111111111", "12/26", "123", order.getTotal());

        if (success) {
            EmailService email = new EmailService();
            email.sendConfirmation("customer@example.com", order);
        } else {
            System.out.println("Payment failed. Please check your card details.");
        }
    }
}