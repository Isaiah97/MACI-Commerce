package app;

import model.Bouquet;
import model.Order;
import model.OrderStatus;
import service.CatalogService;
import service.OrderService;
import service.AuditLogger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AdminFrame extends JFrame {

    private final CatalogService catalogService;
    private final OrderService orderService;
    private final AuditLogger logger;

    // Orders tab components
    private JTable ordersTable;
    private DefaultTableModel ordersModel;

    // Catalog tab components
    private JTable catalogTable;
    private DefaultTableModel catalogModel;

    public AdminFrame(CatalogService catalogService,
                      OrderService orderService,
                      AuditLogger logger) {
        this.catalogService = catalogService;
        this.orderService = orderService;
        this.logger = logger;

        setTitle("Admin Panel - MACI Commerce");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // only closes this window
        setSize(900, 600);
        setLocationRelativeTo(null);

        initUI();
    }

    // ===================== MAIN UI LAYOUT =====================
    private void initUI() {
        setLayout(new BorderLayout());

        // ----- Header with Back / Logout -----
        JButton backButton = new JButton("Back to Customer Menu");
        backButton.addActionListener(e -> {
            this.dispose();
            new FloralShopFrame(catalogService, orderService).setVisible(true);
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            this.dispose();
            new FloralShopFrame(catalogService, orderService).setVisible(true);
        });

        JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        header.add(backButton);
        header.add(logoutButton);
        add(header, BorderLayout.NORTH);

        // ----- Tabs -----
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Orders", createOrdersTab());
        tabs.addTab("Catalog", createCatalogTab());
        add(tabs, BorderLayout.CENTER);
    }

    // ===================== ORDERS TAB =====================
    private JPanel createOrdersTab() {
        JPanel panel = new JPanel(new BorderLayout());

        ordersModel = new DefaultTableModel(
                new Object[]{"Order ID", "Items", "Shipping", "Total", "Status"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only table
            }
        };

        ordersTable = new JTable(ordersModel);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(ordersTable), BorderLayout.CENTER);

        // Buttons to change status
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton pendingButton   = new JButton("Mark as Pending");
        JButton shippedButton   = new JButton("Mark as Shipped");
        JButton deliveredButton = new JButton("Mark as Delivered");
        JButton cancelledButton = new JButton("Mark as Cancelled");

        pendingButton.addActionListener(e -> updateOrderStatus(OrderStatus.PENDING));
        shippedButton.addActionListener(e -> updateOrderStatus(OrderStatus.SHIPPED));
        deliveredButton.addActionListener(e -> updateOrderStatus(OrderStatus.DELIVERED));
        cancelledButton.addActionListener(e -> updateOrderStatus(OrderStatus.CANCELLED));

        buttonsPanel.add(pendingButton);
        buttonsPanel.add(shippedButton);
        buttonsPanel.add(deliveredButton);
        buttonsPanel.add(cancelledButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        // fill table initially
        refreshOrdersTable();

        return panel;
    }

    private void refreshOrdersTable() {
        ordersModel.setRowCount(0);

        List<Order> orders = orderService.getAllOrders();
        for (Order o : orders) {
            String itemNames = o.getItems().stream()
                    .map(Bouquet::getName)
                    .collect(Collectors.joining(", "));

            ordersModel.addRow(new Object[]{
                    o.getOrderId(),
                    itemNames,
                    o.getShippingMethod(),
                    o.getTotal(),
                    o.getStatus()
            });
        }
    }

    private void updateOrderStatus(OrderStatus newStatus) {
        int row = ordersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an order first.");
            return;
        }

        String orderId = (String) ordersTable.getValueAt(row, 0);

        Order selected = orderService.getAllOrders().stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Order not found.");
            return;
        }

        selected.setStatus(newStatus);
        logger.log("Order " + orderId + " updated to " + newStatus, "admin");
        refreshOrdersTable();
    }

    // ===================== CATALOG TAB =====================
    private JPanel createCatalogTab() {
        JPanel panel = new JPanel(new BorderLayout());

        catalogModel = new DefaultTableModel(
                new Object[]{"Name", "Category", "Price", "Available"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only
            }
        };

        catalogTable = new JTable(catalogModel);
        panel.add(new JScrollPane(catalogTable), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshCatalogTable());
        buttons.add(refreshBtn);

        JLabel note = new JLabel("Catalog is read-only in this version.");
        buttons.add(note);

        panel.add(buttons, BorderLayout.SOUTH);

        // fill table initially
        refreshCatalogTable();

        return panel;
    }

    private void refreshCatalogTable() {
        catalogModel.setRowCount(0);

        List<Bouquet> bouquets = catalogService.getAll();
        for (Bouquet b : bouquets) {
            catalogModel.addRow(new Object[]{
                    b.getName(),
                    b.getCategory(),
                    b.getPrice(),
                    b.isAvailable() ? "Available" : "Unavailable"
            });
        }
    }
}
