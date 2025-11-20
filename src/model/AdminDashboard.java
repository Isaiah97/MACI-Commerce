package model;

import model.Bouquet;
import model.Order;
import java.util.List;

public class AdminDashboard {
    public void displayCatalog(List<Bouquet> catalog) {
        System.out.println("📦 Product Catalog:");
        catalog.forEach(System.out::println);
    }

    public void displayOrders(List<Order> orders) {
        System.out.println("📋 Incoming Orders:");
        orders.forEach(System.out::println);
    }
}
