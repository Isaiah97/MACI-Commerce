package service;

import java.time.LocalDateTime;

public class AuditLogger {
    public void log(String action, String actor) {
        System.out.println("🔒 AUDIT LOG [" + LocalDateTime.now() + "] - " + actor + ": " + action);
    }
}
