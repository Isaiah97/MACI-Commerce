package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DbConnection {

    private static Connection connection;

    public static Connection getConnection() throws Exception {

        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        // Load properties file
        Properties props = new Properties();
        try (InputStream input = DbConnection.class.getClassLoader()
                                                   .getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("db.properties not found in resources folder.");
            }
            props.load(input);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.username");
        String pass = props.getProperty("db.password");

        // Load Oracle JDBC driver (optional but safe)
        Class.forName("oracle.jdbc.OracleDriver");

        connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }
}
