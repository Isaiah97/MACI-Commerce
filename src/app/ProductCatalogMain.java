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

        System.out.println("Full Catalog: ");
        for (Bouquet b : catalog.getAll()){
            System.out.println(b)
        }
        
        List<Bouquet> results = catalog.search("Rose", "Roses", 10, 50);
        System.out.println("\nSearch Results (\"Rose\", category \"Roses\", price 10–50):");
        for (Bouquet b : results) {
            System.out.println(b);
        }

        // Create an order from the search results
        if (!results.isEmpty()) {
            Order order = new Order(results, ShippingMethod.EXPRESS);

            // Process payment
            PaymentProcessor payment = new PaymentProcessor();
            double totalAmount = order.getTotal();   // uses your Order.getTotal()
            boolean paid = payment.processPayment("4111111111111111", "12/26", "123", totalAmount);

            if (paid) {
                System.out.println("\nPayment approved for order:");
                System.out.println(order);

                // Send confirmation email
                EmailService email = new EmailService();
                email.sendOrderConfirmation(order);
            } else {
                System.out.println("\nPayment failed for order:");
                System.out.println(order);
            }
        } else {
            System.out.println("\nNo bouquets matched the search criteria.");
        }
    }
}