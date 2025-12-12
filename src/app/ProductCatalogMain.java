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

        //  FULL CATALOG
        sectionHeader("FULL PRODUCT CATALOG");
        for (Bouquet b : catalog.getAll()) {
            printBouquetLine(b);
        }

        //  SEARCH RESULTS
        List<Bouquet> results = catalog.search("Rose", "Roses", 10, 50);

        sectionHeader("SEARCH RESULTS (\"Rose\", category \"Roses\", price 10–50)");
        if (results.isEmpty()) {
            System.out.println("No bouquets matched the search criteria.");
            return;
        }

        for (Bouquet b : results) {
            printBouquetLine(b);
        }

        //  ORDER & PAYMENT
        Order order = new Order(results, ShippingMethod.EXPRESS);

        sectionHeader("PAYMENT PROCESSING");
        PaymentProcessor payment = new PaymentProcessor();
        double totalAmount = order.getTotal();
        boolean paid = payment.processPayment("4111111111111111", "12/26", "123", totalAmount);

        if (paid) {
            System.out.println("✔ Payment approved for order.\n");
            sectionHeader("ORDER SUMMARY");
            printOrderSummary(order);

            // Send confirmation email
            EmailService email = new EmailService();
            email.sendConfirmation(order);
        } else {
            System.out.println("❌ Payment failed for order.\n");
            sectionHeader("ORDER SUMMARY");
            printOrderSummary(order);
        }
    }

    //        Helper Formatting

    private static void sectionHeader(String title) {
        System.out.println("\n========================================");
        System.out.println("           " + title);
        System.out.println("========================================");
    }

    private static void printBouquetLine(Bouquet b) {
        System.out.printf("• %-30s (%s)  $%-6.2f  [%s]%n",
                b.getName(),
                b.getDescription(),
                b.getCategory(),
                b.getPrice(),
                b.isAvailable() ? "Available" : "Unavailable");
    }

    private static void printOrderSummary(Order order) {
        //System.out.printf("\nOrder ID: %s%n", order.getOrderId());
        System.out.println("\nItems:");
        for (Bouquet b : order.getItems()) {
            System.out.printf("  - %-30s $%-6.2f [%s]%n",
                    b.getName(),
                    b.getPrice());
        }

        System.out.printf("\nShipping: %-15s%n", order.getShippingMethod());
        System.out.printf("Total: $%.2f%n", order.getTotal());
        System.out.printf("Status: %s%n", order.getStatus());
        System.out.printf("Delivery Status: %s%n", order.getDeliveryStatus());
    }
}
