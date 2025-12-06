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

			AuditLogger logger = new AuditLogger();   // or whatever constructor your project uses

			AdminFrame frame = new AdminFrame(catalogService, orderService, logger);
			frame.setVisible(true);
		});
	}

            FloralShopFrame frame = new FloralShopFrame(catalogService, orderService);
            frame.setVisible(true);
        });
    }
}
