package ru.clevertec.task.repositories.user;

import ru.clevertec.task.db.DbConnection;
import ru.clevertec.task.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserRepositoryImpl implements UserRepository {
    private UserRepositoryImpl() {
    }

    private static class Holder {
        private static final UserRepository INSTANCE = new UserRepositoryImpl();
    }

    public static UserRepository getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public UUID createUser(User user) {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);
            String query = "INSERT INTO users(login, password, phonenumber, firstname, surname) VALUES (?,?,?,?,?)";

            try (PreparedStatement statement = connection.prepareStatement(query, RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getPhoneNumber());
                statement.setString(4, user.getFirstname());
                statement.setString(5, user.getSurname());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        UUID userId = (UUID) generatedKeys.getObject(1);
                        connection.commit();
                        return userId;
                    } else {
                        connection.rollback();
                        throw new SQLException("The transaction was rolled back");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UUID getUser(User user) {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setReadOnly(true);

            String query = "SELECT id FROM users WHERE login = ? and password = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return (UUID) resultSet.getObject("id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
