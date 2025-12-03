package service;

import model.*;
import ui.AdminDashboard;
import security.AuthService;
import logging.AuditLogger;


/**
*TODO: Implemnts authentication logic
*/

public class AuthService {
    private final Admin admin = new Admin("admin", "secure123");

    public boolean authenticate(String username, String password) {
        return admin.getUsername().equals(username) && admin.getPassword().equals(password);
    }
}


