package model;

public class Bouquet {
    private String name;
    private String description;
    private String category;
    private double price;
    private boolean available;

    // name, description, category, price, available
    public Bouquet(String name, String description, String category, double price, boolean available) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.available = available;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public boolean isInStock() { return inStock; }
    public boolean isAvailable() { return inStock; }

    public boolean isAvailable() { return available; }

    @Override
    public String toString() {
        return name + " (" + category + ") - $" + price
                + (available ? " [Available]" : " [Unavailable]");
    }
}
