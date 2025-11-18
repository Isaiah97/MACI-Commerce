package service;

import model.*; //error

public class DeliveryTracker {
    public void updateDeliveryStatus(Order order) { //error
        if (order.getStatus() == OrderStatus.PAID) { //error
            order.setDeliveryStatus(DeliveryStatus.IN_TRANSIT); //error 
        } else {
            order.setDeliveryStatus(DeliveryStatus.FAILED_TO_UPDATE); //error
        }
    }

    public void markAsDelivered(Order order) { //error
        if (order.getDeliveryStatus() == DeliveryStatus.IN_TRANSIT) { //error
            order.setDeliveryStatus(DeliveryStatus.DELIVERED); //error
            order.setStatus(OrderStatus.DELIVERED); //error
        }
    }

    public void printTrackingInfo(Order order) { //error
        System.out.println("📦 Tracking Info for Order " + order.getOrderId() + ": " + order.getDeliveryStatus());
    }
}
