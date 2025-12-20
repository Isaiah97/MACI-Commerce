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
// will use this to develop helpers

//all needed fields the team has came up with
    //more additions can be added
    public Order(List<Bouquet> items, ShippingMethod shippingMethod) {
        this.orderId = UUID.randomUUID().toString();
        this.items = items;
        this.shippingMethod = shippingMethod;
        this.status = OrderStatus.PENDING;
        this.deliveryStatus = DeliveryStatus.NOT_SHIPPED;
        calculateTotal();
    }

//redundant zero issue fixed
    //private helper used to calculate the bouquet price and shipping price
    private void calculateTotal() {
        double rawTotal = items.stream().mapToDouble(Bouquet::getPrice).sum() + shippingMethod.getCost();

        BigDecimal rounded = BigDecimal.valueOf(rawTotal).setScale(2, RoundingMode.HALF_UP);
        this.total = rounded.doubleValue();

    }

    //returns item id
    public String getOrderId() { 
        return orderId; 
    }
   //returns the total of the spendings
    public double getTotal() { 
        return total;
    }
    //displays the list of the products in our catalog
    public List<Bouquet> getItems() { 
        return items; 
    }

    //returns the shipping method of user's choice
    public ShippingMethod getShippingMethod() { 
        return shippingMethod;
    }

    //returns the status on item availability
    public OrderStatus getStatus() { 
        return status;
    }

    //Admins can set the availability status on products
    public void setStatus(OrderStatus status) { 
        this.status = status;
    }

    //returns the status on the delivery of the product set by the Admins
    public DeliveryStatus getDeliveryStatus() { 
        return deliveryStatus;
    }

    //allows admins to set the status of the delivery thru the admin panel
    public void setDeliveryStatus(DeliveryStatus deliveryStatus) { 
        this.deliveryStatus = deliveryStatus;
    }

//adding all return values into one compile string storing order 1d, shipping method, the cost of shipping method,
//estimated delivery type and time, total of all costs, status of the item, and delivery status
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
    public List<Bouquet> getBouquets() {
        return this.items;
    }
}
