package app;

import model.*;
import service.*;

public class SecurityMain{
    public static void main(String[] args) {
// Admin login and audit
AuthService auth = new AuthService();
AuditLogger logger = new AuditLogger();
AdminDashboard dashboard = new AdminDashboard();

if (auth.authenticate("admin", "secure123")) {
    logger.log("Admin logged in", "admin");
    dashboard.displayCatalog(catalog.getAll());
    dashboard.displayOrders(orderService.getAllOrders());
} else {
    System.out.println("❌ Unauthorized access attempt.");
        }
    }
}