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

	private void initComponents() {
		setLayout(new Border());

		JLabel title = new JLabel("MACI Commerce", SwingConstants.CENTER);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
		add(title, Border.NORTH);

		JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));

		JPanel catalogPanel = bew JPanel(new Border());
		catalogPanel.setBorder(BorderFactory.createTitledBorder("Catalog"));
		catalogList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        catalogPanel.add(new JScrollPane(catalogList), BorderLayout.CENTER);

        JButton addToCartBtn = new JButton("Add to Order ➜");
        addToCartBtn.addActionListener(this::onAddToOrder);
        catalogPanel.add(addToCartBtn, BorderLayout.SOUTH);

        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Current Order"));
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        centerPanel.add(catalogPanel);
        centerPanel.add(cartPanel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        orderSummaryArea.setEditable(false);
        orderSummaryArea.setBorder(BorderFactory.createTitledBorder("Order Summary"));
        bottomPanel.add(new JScrollPane(orderSummaryArea), BorderLayout.CENTER);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.addActionListener(this::onCheckout);

        JButton adminBtn = new JButton("Admin Panel");
        adminBtn.addActionListener(this::onAdminPanel);

        buttonRow.add(adminBtn);
        buttonRow.add(checkoutBtn);

        bottomPanel.add(buttonRow, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}
    