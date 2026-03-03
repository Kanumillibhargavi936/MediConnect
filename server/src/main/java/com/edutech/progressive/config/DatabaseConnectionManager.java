package com.edutech.progressive.config;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DatabaseConnectionManager {
    private static final Properties properties = new Properties();
    private static void loadProperties() {
        if (!properties.isEmpty()) return;
        try (InputStream in = DatabaseConnectionManager.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (in == null) throw new RuntimeException("application.properties not found");
            properties.load(in);
            String driver = properties.getProperty("spring.datasource.driver-class-name");
            if (driver != null && !driver.isBlank()) Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB properties", e);
        }
    }
    public static Connection getConnection() throws SQLException {
        loadProperties();
        String url = properties.getProperty("spring.datasource.url");
        String username = properties.getProperty("spring.datasource.username");
        String password = properties.getProperty("spring.datasource.password");
        if (url == null || username == null || password == null) {
            throw new SQLException("Missing spring.datasource properties");
        }
        return DriverManager.getConnection(url, username, password);
    }
}
 