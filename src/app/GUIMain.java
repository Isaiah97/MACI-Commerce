package app;

import javax.swing.SwingUtilities;
import service.CatalogService;
import service.OrderService;

public class GUIMain {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			CatalogService catalogService = new CatalogService();
			OrderService orderService = new OrderService();

			FloralShopFrame frame = new FloralShopFrame(CatalogService, OrderService);
			frame.setVisible(true);
		});
	}

}