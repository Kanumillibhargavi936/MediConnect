package com.edutech.progressive.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnectionManager {
private static final Properties properties=new Properties();

    static
    {
        loadProperties();
    }

    private static void loadProperties()
    {
        try(InputStream input=DatabaseConnectionManager.class.getClassLoader().getResourceAsStream("application.properties"))
        {
            if(input==null)
            {
                throw new IllegalStateException("resource.properties not found in classpath");
            }
            properties.load(input);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Error loading properties file",e);
        }

    }

    public static Connection getConnection() throws SQLException
    {
        loadProperties();

        String url=properties.getProperty("spring.datasource.url");
        String username=properties.getProperty("spring.datasource.username");
        String password=properties.getProperty("spring.datasource.password");

        if(url==null || url.isBlank())
        {
            throw new IllegalStateException("Missing property: spring.datasource.url");
        }

        if(username==null || username.isBlank())
        {
            throw new IllegalStateException("Missing property: spring.datasource.username");
        }

        return DriverManager.getConnection(url, username, password);

    }
}
