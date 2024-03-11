package ru.clevertec.task.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static ru.clevertec.task.constants.Constants.*;
public class DbConnection {
    private DbConnection() {
    }

    public static Connection getConnection() {
        loadingDriver();

        try {
            Properties properties = new Properties();
            InputStream input = DbConnection.class.getClassLoader().getResourceAsStream(CONFIG);
            properties.load(input);

            String url = properties.getProperty(DB_URL);
            String username = properties.getProperty(DB_USERNAME);
            String password = properties.getProperty(DB_PASSWORD);

            return DriverManager.getConnection(url, username, password);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish database connection.");
        }
    }

    private static void loadingDriver() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("-------JDBC Driver not found.-------");
            e.printStackTrace();
        }
    }
}
