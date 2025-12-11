package model;

import java.util.List;
import java.util.UUID;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class Order {

// fields in the Order.java program

    private String orderId;
    private List<Bouquet> items;
    private ShippingMethod shippingMethod;
    private double total;
    private OrderStatus status;
    private DeliveryStatus deliveryStatus;


//constructor for the Order.java program
    public Order(List<Bouquet> items, ShippingMethod shippingMethod) {
        this.orderId = UUID.randomUUID().toString();
        this.items = items;
        this.shippingMethod = shippingMethod;
        this.status = OrderStatus.PENDING;
        this.deliveryStatus = DeliveryStatus.NOT_SHIPPED;
        calculateTotal();
    }

    private void calculateTotal() {
        total = items.stream().mapToDouble(Bouquet::getPrice).sum() + shippingMethod.getCost()
        .setScale(2, RoundingMode.HALF_UP);
    }

    
    public String getOrderId() { 
        return orderId; 
    }
   
    public double getTotal() { 
        return total;
    }

    public List<Bouquet> getItems() { 
        return items; 
    }

    public ShippingMethod getShippingMethod() { 
        return shippingMethod;
    }

    public OrderStatus getStatus() { 
        return status;
    }
    public void setStatus(OrderStatus status) { 
        this.status = status;
    }

    public DeliveryStatus getDeliveryStatus() { 
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) { 
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order ID: " + orderId + "\n");
        items.forEach(b -> sb.append("- ").append(b.toString()).append("\n"));
        sb.append("Shipping: ").append(shippingMethod.name())
          .append(" ($").append(shippingMethod.getCost()).append(")\n")
          .append("Estimated Delivery: ").append(shippingMethod.getDeliveryDays()).append(" days\n")
          .append("Total: $").append(total).append("\n")
          .append("Status: ").append(status).append("\n")
          .append("Delivery Status: ").append(deliveryStatus);
        return sb.toString();
    }
}
