package model;

public class Bouquet {
    private String id;
    private String name;
    private String category;
    private double price;
    private boolean inStock;
    private boolean available;

    public Bouquet(String id, String name, String category, double price, boolean inStock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.inStock = inStock;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public boolean isInStock() { return inStock; }
    public boolean isAvailable() { return inStock; }

    public boolean isAvailable() { return available; }
    

    @Override
    public String toString() {
        return name + " (" + category + ") - $" + price + (inStock ? " [Available]" : " [Out of Stock]");
    }
}