package service;

import model.*;

/**
 * TODO: Implement email sending logic
 */

public class EmailService {
    public void sendConfirmation(String toEmail, Order order) {
        String subject = "🌸 Your Bouquet Order Confirmation";
        String body = "Dear Customer,\n\nThank you for your purchase!\n\n" +
                      order.toString() + "\n\nWe hope your flowers bring joy!\n\nBouquet Sales Co.";
        System.out.println("Sending email to " + toEmail + "...\nSubject: " + subject + "\n\n" + body);
    }
}

