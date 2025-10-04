package com.abc.ecommerce.model;

public class Product {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private boolean available;

    // Constructors
    public Product() {}
    public Product(Long id, String name, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.available = quantity > 0;
    }
}
