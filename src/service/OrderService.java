package service;

import model.*;
import java.util.*;

/**
 * 
 * TODO: implementation of OrderService
 */

public class OrderService {
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public Optional<Order> getOrderById(String orderId) {
        return orders.stream().filter(o -> o.getOrderId().equals(orderId)).findFirst();
    }

    public boolean updateOrderStatus(String orderId, OrderStatus status) {
        Optional<Order> orderOpt = getOrderById(orderId);
        if (orderOpt.isPresent()) {
            orderOpt.get().setStatus(status);
            return true;
        }
        return false;
    }
    //Remove from Cart feature
    public boolean removeBouquetFromOrder (String orderId, String bouquetName) {
        Optional<Order> orderOpt.get();
        return order.getBouquets().removeIf(b -> b.getName().equalsIgnoreCase(bouquetName));
    }
    return false;
    }
}

