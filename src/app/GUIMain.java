package app;

import javax.swing.SwingUtilities;
import service.CatalogService;
import service.OrderService;
import service.AuditLogger;

public class GUIMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CatalogService catalogService = new CatalogService();
            OrderService orderService = new OrderService();
            AuditLogger logger = new AuditLogger();

            FloralShopFrame frame = new FloralShopFrame(catalogService, orderService);
            frame.setVisible(true);

            // Page starter for us admins:
            // AdminFrame admin = new AdminFrame(catalogService, orderService, logger);
            // admin.setVisible(true);
        });
    }
}
