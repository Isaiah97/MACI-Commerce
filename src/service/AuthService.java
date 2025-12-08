package service;

import model.Admin;


/**
*TODO: Implemnts authentication logic
*/

public class AuthService {
    private final Admin admin = new Admin("admin", "secure123");
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



