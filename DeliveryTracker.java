package service;

import model.*;

public class DeliveryTracker {
    public void updateDeliveryStatus(Order order) {
        if (order.getStatus() == OrderStatus.PAID) {
            order.setDeliveryStatus(DeliveryStatus.IN_TRANSIT);
        } else {
            order.setDeliveryStatus(DeliveryStatus.FAILED_TO_UPDATE);
        }
    }

    public void markAsDelivered(Order order) {
        if (order.getDeliveryStatus() == DeliveryStatus.IN_TRANSIT) {
            order.setDeliveryStatus(DeliveryStatus.DELIVERED);
            order.setStatus(OrderStatus.DELIVERED);
        }
    }

    public void printTrackingInfo(Order order) {
        System.out.println("📦 Tracking Info for Order " + order.getOrderId() + ": " + order.getDeliveryStatus());
    }
}
