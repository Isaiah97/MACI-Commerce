package model;

public enum ShippingMethod {
    STANDARD(5.99, 5),
    EXPRESS(12.99, 2);

    private final double cost;
    private final int deliveryDays;

    ShippingMethod(double cost, int deliveryDays) {
        this.cost = cost;
        this.deliveryDays = deliveryDays;
    }

    public double getCost() { return cost; }
    public int getDeliveryDays() { return deliveryDays; }
}
