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
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

    
    private void initUI() {

    // ===== BACK TO CUSTOMER MENU BUTTON =====
    JButton backButton = new JButton("Back to Customer Menu");
    backButton.addActionListener(e -> {
        this.dispose();  // close admin window
        new FloralShopFrame(catalogService, orderService).setVisible(true);
    });

    // ===== LOGOUT BUTTON =====
    JButton logoutButton = new JButton("Logout");
    logoutButton.addActionListener(e -> {
        this.dispose();  
        // If logout should return to login instead of customer menu:
        // new LoginFrame().setVisible(true);
        new FloralShopFrame(catalogService, orderService).setVisible(true);
    });

    // Header panel (top of the window)
    JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    header.add(backButton);
    header.add(logoutButton);

    add(header, BorderLayout.NORTH);

    // ===== Tabs =====
    JTabbedPane tabs = new JTabbedPane();
    tabs.addTab("Orders", createOrdersTab());
    tabs.addTab("Catalog", createCatalogTab());
    add(tabs, BorderLayout.CENTER);
}



    // ===================== ORDERS TAB =====================
private JPanel createOrdersTab() {
    JPanel panel = new JPanel(new BorderLayout());

    // Table model with column names matching refreshOrdersTable()
    ordersModel = new DefaultTableModel(
        new Object[] { "Order ID", "Items", "Shipping", "Total", "Status" },
        0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // make table read-only
            return false;
        }
    };

    // Table
    ordersTable = new JTable(ordersModel);
    ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JScrollPane scrollPane = new JScrollPane(ordersTable);
    panel.add(scrollPane, BorderLayout.CENTER);

    // ----- Buttons to change status -----
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
    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Orders", createOrdersTab());
        tabs.addTab("Catalog", createCatalogTab());
        add(tabs);
    }

    // ===================== ORDERS TAB =====================

    private JPanel createOrdersTab() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] cols = {"Order ID", "Items", "Shipping", "Total", "Status"};
        ordersModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        ordersTable = new JTable(ordersModel);
        refreshOrdersTable();

        JPanel buttons = new JPanel();
        JButton shippedBtn = new JButton("Mark as SHIPPED");
        JButton deliveredBtn = new JButton("Mark as DELIVERED");

        shippedBtn.addActionListener(e -> updateOrderStatus(OrderStatus.SHIPPED));
        deliveredBtn.addActionListener(e -> updateOrderStatus(OrderStatus.DELIVERED));

        buttons.add(shippedBtn);
        buttons.add(deliveredBtn);

        panel.add(new JScrollPane(ordersTable), BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

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

        // assumes your Order has setStatus(OrderStatus)
        selected.setStatus(newStatus);
        logger.log("Order " + orderId + " updated to " + newStatus, "admin");

        refreshOrdersTable();
    }

    // ===================== CATALOG TAB (READ-ONLY) =====================

    private JPanel createCatalogTab() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] cols = {"Name", "Category", "Price", "Available"};
        catalogModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        catalogTable = new JTable(catalogModel);
        refreshCatalogTable();

        JPanel buttons = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> refreshCatalogTable());
        buttons.add(refreshBtn);

        JLabel note = new JLabel("Catalog is read-only in this version.");
        buttons.add(note);

        panel.add(new JScrollPane(catalogTable), BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshCatalogTable() {
    catalogModel.setRowCount(0);

    List<Bouquet> bouquets = catalogService.getAll();
    for (Bouquet b : bouquets) {
        catalogModel.addRow(new Object[]{
                b.getName(),
                b.getCategory(),
                b.getPrice()
        });
    }
}

}

        catalogModel.setRowCount(0);

        List<Bouquet> bouquets = catalogService.getAll();
        for (Bouquet b : bouquets) {
            catalogModel.addRow(new Object[]{
                    b.getName(),
                    b.getCategory(),
                    b.getPrice(),
                    b.isAvailable()
            });
        }
    }
}
