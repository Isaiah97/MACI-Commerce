package service;



/**
*TODO: Implemnts authentication logic
*/

public class AuthService {

    // Simple demo authentication logic.
    // Change the username/password as needed.
    public boolean authenticate(String username, String password) {
        return "admin".equals(username) && "secure123".equals(password);
    }
}
    