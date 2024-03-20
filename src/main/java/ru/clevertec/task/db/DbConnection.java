package ru.clevertec.task.db;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static ru.clevertec.task.utils.Constants.*;

public class DbConnection {
    private static final String url;
    private static final String username;
    private static final String password;
    private static final int INITIAL_POOL_SIZE = 10;
    private static final BlockingQueue<Connection> connectionsPool = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);

    static {
        Properties properties = new Properties();
        InputStream input = DbConnection.class.getClassLoader().getResourceAsStream(CONFIG);
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        url = properties.getProperty(DB_URL);
        username = properties.getProperty(DB_USERNAME);
        password = properties.getProperty(DB_PASSWORD);

        initializePool();
    }

    private DbConnection() {
    }

    private static Connection createConnection() {
        loadingDriver();
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Connection failed");
        }
    }

    @SneakyThrows
    private static void initializePool() {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            Connection connection = createConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connectionsPool.offer(connection);
        }
    }

    private static void loadingDriver() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found");
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static Connection getConnection() {
        return connectionsPool.take();
    }

    public static void releaseConnection(Connection connection) {
        connectionsPool.offer(connection);
    }
}
