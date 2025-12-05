package app;

import model.Bouquet;
import model.Order;
import model.OrderStatus;
import model.DeliveryStatus;
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

    public AdminFrame(CatalogService catalogService, OrderService orderService, AuditLogger logger) {
        this.catalogService = catalogService;
        this.orderService = orderService;
        this.logger = logger;

        setTitle("Admin Panel - MACI Commerce");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // closes admin window only
        setSize(900, 600);
        setLocationRelativeTo(null);

        initUI();
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

        String[] cols = {"Order ID", "Items", "Shipping", "Total", "Status", "Delivery"};
        ordersModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // table is read-only
            }
        };
        ordersTable = new JTable(ordersModel);
        refreshOrdersTable();

        JPanel buttons = new JPanel();
        JButton shippedBtn = new JButton("Mark as SHIPPED");
        JButton deliveredBtn = new JButton("Mark as DELIVERED");

        shippedBtn.addActionListener(e -> updateOrderStatus(OrderStatus.SHIPPED, DeliveryStatus.SHIPPED));
        deliveredBtn.addActionListener(e -> updateOrderStatus(OrderStatus.DELIVERED, DeliveryStatus.DELIVERED));

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
                    o.getStatus(),
                    o.getDeliveryStatus()
            });
        }
    }

    private void updateOrderStatus(OrderStatus newStatus, DeliveryStatus newDeliveryStatus) {
        int row = ordersTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an order first.");
            return;
        }

        String orderId = (String) ordersTable.getValueAt(row, 0);

        // simple lookup from the service list
        Order selected = orderService.getAllOrders().stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElse(null);

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Order not found.");
            return;
        }

        // You may need to adjust these setters to match your Order class
        selected.setStatus(newStatus);
        selected.setDeliveryStatus(newDeliveryStatus);

        logger.log("Order " + orderId + " updated to " + newStatus, "admin");

        refreshOrdersTable();
    }

    // ===================== CATALOG TAB =====================

    private JPanel createCatalogTab() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] cols = {"Code", "Name", "Category", "Price", "Available"};
        catalogModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        catalogTable = new JTable(catalogModel);
        refreshCatalogTable();

        JPanel buttons = new JPanel();
        JButton toggleBtn = new JButton("Toggle Availability");
        JButton editPriceBtn = new JButton("Edit Price");
        JButton addBtn = new JButton("Add Bouquet");

        toggleBtn.addActionListener(e -> toggleAvailability());
        editPriceBtn.addActionListener(e -> editPrice());
        addBtn.addActionListener(e -> addBouquet());

        buttons.add(toggleBtn);
        buttons.add(editPriceBtn);
        buttons.add(addBtn);

        panel.add(new JScrollPane(catalogTable), BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshCatalogTable() {
        catalogModel.setRowCount(0);

        List<Bouquet> bouquets = catalogService.getAll();
        for (Bouquet b : bouquets) {
            catalogModel.addRow(new Object[]{
                    b.getCode(),       // adjust if your field name is different
                    b.getName(),
                    b.getCategory(),
                    b.getPrice(),
                    b.isAvailable()
            });
        }
    }

    private void toggleAvailability() {
        int row = catalogTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a bouquet first.");
            return;
        }

        String code = (String) catalogTable.getValueAt(row, 0);
        Bouquet b = catalogService.findByCode(code); // you may need to add this

        if (b == null) return;

        b.setAvailable(!b.isAvailable());
        logger.log("Toggled availability for bouquet " + code, "admin");

        refreshCatalogTable();
    }

    private void editPrice() {
        int row = catalogTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a bouquet first.");
            return;
        }

        String code = (String) catalogTable.getValueAt(row, 0);
        Bouquet b = catalogService.findByCode(code); // you may need to add this

        if (b == null) return;

        String input = JOptionPane.showInputDialog(this, "New price:", b.getPrice());
        if (input == null) return;

        try {
            double newPrice = Double.parseDouble(input);
            b.setPrice(newPrice);
            logger.log("Changed price for bouquet " + code + " to " + newPrice, "admin");
            refreshCatalogTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price.");
        }
    }

    private void addBouquet() {
        String code = JOptionPane.showInputDialog(this, "Code/ID:");
        if (code == null || code.isBlank()) return;

        String name = JOptionPane.showInputDialog(this, "Name:");
        if (name == null || name.isBlank()) return;

        String category = JOptionPane.showInputDialog(this, "Category:");
        if (category == null || category.isBlank()) return;

        String priceStr = JOptionPane.showInputDialog(this, "Price:");
        if (priceStr == null) return;

        try {
            double price = Double.parseDouble(priceStr);
            Bouquet b = new Bouquet(code, name, category, price, true);
            catalogService.addBouquet(b); // you may need to implement this
            logger.log("Added new bouquet " + code, "admin");

            refreshCatalogTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid price.");
        }
    }
}
