package model;

import java.util.List;
import java.util.UUID;

public class Order {
    private String orderId;
    private List<Bouquet> items;
    private ShippingMethod shippingMethod;
    private double total;
    private OrderStatus status;

    public Order(List<Bouquet> items, ShippingMethod shippingMethod) {
        this.orderId = UUID.randomUUID().toString();
        this.items = items;
        this.shippingMethod = shippingMethod;
        this.status = OrderStatus.PENDING;
        calculateTotal();
    }

    private void calculateTotal() {
        total = items.stream().mapToDouble(Bouquet::getPrice).sum() + shippingMethod.getCost();
    }

    public String getOrderId() { return orderId; }
    public double getTotal() { return total; }
    public List<Bouquet> getItems() { return items; }
    public ShippingMethod getShippingMethod() { return shippingMethod; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order ID: " + orderId + "\n");
        items.forEach(b -> sb.append("- ").append(b.toString()).append("\n"));
        sb.append("Shipping: ").append(shippingMethod.name())
          .append(" ($").append(shippingMethod.getCost()).append(")\n")
          .append("Estimated Delivery: ").append(shippingMethod.getDeliveryDays()).append(" days\n")
          .append("Total: $").append(total).append("\n")
          .append("Status: ").append(status);
        return sb.toString();
    }
}
