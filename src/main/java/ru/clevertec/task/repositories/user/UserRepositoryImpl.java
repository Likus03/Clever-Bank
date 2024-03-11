package ru.clevertec.task.repositories.user;

import ru.clevertec.task.db.DbConnection;

import java.sql.*;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepository userRepository;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepositoryImpl();
        }
        return userRepository;
    }

    @Override
    public UUID createUser(String login, String password, String phoneNumber, String firstname, String surname) {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);
            String query = "INSERT INTO users(login, password, phonenumber, firstname, surname) VALUES (?,?,?,?,?)";

            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, login);
                statement.setString(2, password);
                statement.setString(3, phoneNumber);
                statement.setString(4, firstname);
                statement.setString(5, surname);
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return (UUID) generatedKeys.getObject(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    connection.rollback();
                }
            } finally {
                try {
                    connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public UUID getUser(String login, String password) {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setReadOnly(true);

            String query = "SELECT * FROM users WHERE login = ? and password = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, login);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return (UUID) resultSet.getObject("id");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
