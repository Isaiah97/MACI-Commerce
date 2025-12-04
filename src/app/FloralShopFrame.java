package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import model.Bouquet;
import model.Order;
import model.ShippingMethod;
import service.CatalogService;
import service.OrderService;
import service.PaymentProcessor;
import service.EmailService;
import service.AuthService;
import service.AdminDashboard;
import service.AuditLogger;

public class FloralShopFrame {

	private final CatalogService catalogService;
	private final OrderService orderService;

	private final DefaultListModel<Bouquet> catalogListModel = new DefaultListModel<>();
	private final JList<Bouquet> catalogList = new JList<>(cartListModel);

	private final JTextArea orderSummaryArea = new JTextArea(8, 30);

	public FloralShopFrame(CatalogService catalogService, OrderService orderService) {
		this.catalogService = catalogService;
		this.orderService = orderService;

		setTitle("MACI Commerce");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setLocationRelativeTo(null);

		initComponents();
		loadCatalog();
	}
	
}
