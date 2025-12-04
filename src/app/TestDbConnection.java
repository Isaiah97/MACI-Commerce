package app;

import service.DbConnection;
import java.sql.Connection;

public class TestDbConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DbConnection.getConnection();
            System.out.println("Connected successfully: " + (conn != null && !conn.isClosed()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
