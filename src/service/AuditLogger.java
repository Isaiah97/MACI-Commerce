package service;

import model.*;
import java.time.LocalDateTime;

/**
 * 
 * TODO: audit logger implementation
 * 
 */

public class AuditLogger {
    public void log(String action, String actor) {
        System.out.println("🔒 AUDIT LOG [" + LocalDateTime.now() + "] - " + actor + ": " + action);
    }
}

