package service;

import model.Admin;


/**
*TODO: Implemnts authentication logic
*/

public class AuthService {

    // Simple demo authentication logic.
    // Change the username/password as needed.
    public boolean authenticate(String username, String password) {
        return "admin".equals(username) && "password123".equals(password);
    }

    private void onAdminPanel(ActionEvent e) {
    AuthService auth = new AuthService();
    AuditLogger logger = new AuditLogger();

    // build login dialog UI...
    // get username/password from fields...

    if (result == JOptionPane.OK_OPTION) {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if (auth.authenticate(username, password)) {
            logger.log("Admin logged in via GUI", username);

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


