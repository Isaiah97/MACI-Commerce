package model;

import java.util.List;

public class Order {
    private List<Bouquet> items;
    private ShippingMethod shippingMethod;
    private double total;

    public Order(List<Bouquet> items, ShippingMethod shippingMethod) {
        this.items = items;
        this.shippingMethod = shippingMethod;
        calculateTotal();
    }

    private void calculateTotal() {
        total = items.stream().mapToDouble(Bouquet::getPrice).sum() + shippingMethod.getCost();
    }

    public double getTotal() { return total; }
    public ShippingMethod getShippingMethod() { return shippingMethod; }
    public List<Bouquet> getItems() { return items; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order Summary:\n");
        items.forEach(b -> sb.append("- ").append(b.toString()).append("\n"));
        sb.append("Shipping: ").append(shippingMethod.name())
          .append(" ($").append(shippingMethod.getCost()).append(")\n")
          .append("Estimated Delivery: ").append(shippingMethod.getDeliveryDays()).append(" days\n")
          .append("Total: $").append(total);
        return sb.toString();
    }
}
