package app;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
import service.AuditLogger;

public class FloralShopFrame extends JFrame {

    private final CatalogService catalogService;
    private final OrderService orderService;

    // === CATALOG + SEARCH ===
    private final DefaultListModel<Bouquet> catalogListModel = new DefaultListModel<>();
    private final JList<Bouquet> catalogList = new JList<>(catalogListModel);
    private final JTextField searchField = new JTextField();
    private final List<Bouquet> allBouquets = new ArrayList<>();   // master list

    // === CART / ORDER SUMMARY ===
    private final DefaultListModel<Bouquet> cartListModel = new DefaultListModel<>();
    private final JList<Bouquet> cartList = new JList<>(cartListModel);

    private final JTextArea orderSummaryArea = new JTextArea(8, 30);

    public FloralShopFrame(CatalogService catalogService, OrderService orderService) {
        this.catalogService = catalogService;
        this.orderService = orderService;

        setTitle("MACI Commerce - Flower Shop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        initComponents();
        loadCatalog();
    }

    //want all components of a commerce site going to need to research into e-commerce sites
    // need title, catalog with cart, a catalog panel, need a cart panel to view items in the cart,
    // an order summary for the customers to review and to verify
    private void initComponents() {
        setLayout(new BorderLayout());

        // Top title
        JLabel title = new JLabel("MACI Commerce - Flower Shop", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        add(title, BorderLayout.NORTH);

        // Center: catalog + cart
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        // ================= CATALOG PANEL (LEFT) =================
        JPanel catalogPanel = new JPanel(new BorderLayout());
        catalogPanel.setBorder(BorderFactory.createTitledBorder("Catalog"));

        // --- Search bar at top ---
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        JLabel searchLabel = new JLabel("Search: ");
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        catalogPanel.add(searchPanel, BorderLayout.NORTH);

        // when user types, filter the catalog
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                filterCatalog(searchField.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }

            @Override
            public void removeUpdate(DocumentEvent e) { update(); }

            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
        });

        // --- Catalog list ---
        catalogList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        catalogPanel.add(new JScrollPane(catalogList), BorderLayout.CENTER);

        JButton addToCartBtn = new JButton("Add to Order ➜");
        addToCartBtn.addActionListener(this::onAddToOrder);
        catalogPanel.add(addToCartBtn, BorderLayout.SOUTH);

        // ================= CART PANEL (RIGHT) =================
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Current Order"));
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        centerPanel.add(catalogPanel);
        centerPanel.add(cartPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom: order summary + buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());

        orderSummaryArea.setEditable(false);
        orderSummaryArea.setBorder(BorderFactory.createTitledBorder("Order Summary"));
        bottomPanel.add(new JScrollPane(orderSummaryArea), BorderLayout.CENTER);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton adminBtn = new JButton("Admin Panel");
        adminBtn.addActionListener(this::onAdminPanel);

        JButton checkoutBtn = new JButton("Checkout");
        checkoutBtn.addActionListener(this::onCheckout);

         //creating the Remove Button
        JButton removeBtn = new JButton("Remove Selected");
        removeBtn.addActionListener(e -> {
            Bouquet selected = cartList.getSelectedValue(); 
             if (selected != null) {
                cartListModel.removeElement(selected); 
                 updateOrderSummaryPreview(); 
        
                JOptionPane.showMessageDialog(this, "Removed: " + selected.getName());
                } else {
                    JOptionPane.showMessageDialog(this, "Please select an item from the Current Order list to remove.");
            }
        });

        
        buttonRow.add(adminBtn);
        buttonRow.add(checkoutBtn);
        buttonRow.add(removeBtn); //adding the button to the layout

        bottomPanel.add(buttonRow, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Load full catalog from service into master list + show all
    private void loadCatalog() {
        allBouquets.clear();
        allBouquets.addAll(catalogService.getAll());

        filterCatalog("");   // show everything by default
        updateOrderSummaryPreview();
    }

    // === SEARCH FILTER (partial + case-insensitive) ===
    private void filterCatalog(String query) {
        String q = query.trim().toLowerCase();
        catalogListModel.clear();

        if (q.isEmpty()) {
            // empty search → show full catalog
            for (Bouquet b : allBouquets) {
                catalogListModel.addElement(b);
            }
            return;
        }

        for (Bouquet b : allBouquets) {
            String description = b.getDescription();   
            if (description != null && description.toLowerCase().contains(q)) {
                catalogListModel.addElement(b);
            }
        }
    }

    private void onAddToOrder(ActionEvent e) {
        Bouquet selected = catalogList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a bouquet first.");
            return;
        }
        cartListModel.addElement(selected);
        updateOrderSummaryPreview();
    }

    private void updateOrderSummaryPreview() {
        if (cartListModel.isEmpty()) {
            orderSummaryArea.setText("No items in order yet.");
            return;
        }

        List<Bouquet> items = new ArrayList<>();
        for (int i = 0; i < cartListModel.size(); i++) {
            items.add(cartListModel.getElementAt(i));
        }

        Order tempOrder = new Order(items, ShippingMethod.STANDARD);
        orderSummaryArea.setText(tempOrder.toString());
    }

    private void onCheckout(ActionEvent e) {
        if (cartListModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
            return;
        }

        List<Bouquet> items = new ArrayList<>();
        for (int i = 0; i < cartListModel.size(); i++) {
            items.add(cartListModel.getElementAt(i));
        }

        ShippingMethod shipping = ShippingMethod.EXPRESS;
        Order order = new Order(items, shipping);

        PaymentProcessor payment = new PaymentProcessor();
        EmailService email = new EmailService();

        double totalAmount = order.getTotal();
        boolean paid = payment.processPayment("4111111111111111", "12/26", "123", totalAmount);

        if (paid) {
            orderService.addOrder(order);
            email.sendConfirmation(order);

            JOptionPane.showMessageDialog(this,
                    "Payment successful!\nOrder ID: " + order.getOrderId(),
                    "Checkout",
                    JOptionPane.INFORMATION_MESSAGE);

            cartListModel.clear();
            updateOrderSummaryPreview();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Payment failed. Please try again.",
                    "Checkout",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onAdminPanel(ActionEvent e) {
        AuthService auth = new AuthService();
        AuditLogger logger = new AuditLogger();

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField userField = new JTextField("admin");
        JPasswordField passField = new JPasswordField();

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Admin Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (auth.authenticate(username, password)) {
                logger.log("Admin logged in via GUI", username);

                // OPEN THE NEW ADMIN WINDOW
                AdminFrame adminFrame = new AdminFrame(catalogService, orderService, logger);
                adminFrame.setVisible(true);

                JOptionPane.showMessageDialog(this,
                        "Admin logged in. Admin Panel opened.",
                        "Admin Panel",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid admin credentials.",
                        "Admin Panel",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
   

}
                                
