package service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

//import model.Admin;


public class AuthServiceTest {

    @Test
    void testValidLogin() {
        AuthService auth = new AuthService();
        boolean result = auth.authenticate("admin", "secure123");
        assertTrue(result, "Valid admin credentials should authenticate");
    }

    @Test
    void testInvalidUsername() {
        AuthService auth = new AuthService();
        boolean result = auth.authenticate("wrongUser", "secure123");
        assertFalse(result, "Invalid username should not authenticate");
    }

    @Test
    void testInvalidPassword() {
        AuthService auth = new AuthService();
        boolean result = auth.authenticate("admin", "wrongPass");
        assertFalse(result, "Invalid password should not authenticate");
    }
}