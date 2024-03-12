package ru.clevertec.task.repositories.account;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.db.DbConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;

public class AccountRepositoryImpl implements AccountRepository {
    private static AccountRepository accountRepository;

    private AccountRepositoryImpl() {
    }

    public static AccountRepository getInstance() {
        if (accountRepository == null) {
            accountRepository = new AccountRepositoryImpl();
        }
        return accountRepository;
    }

    @Log
    @Override
    public void createAccount(String iban, UUID bankId, UUID userId, String currency) throws SQLException {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);
            String query = "INSERT INTO account(iban, bank_id, user_id, currency, opening_date) VALUES (?,?,?,?,?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, iban);
                statement.setObject(2, bankId);
                statement.setObject(3, userId);
                statement.setString(4, currency);
                statement.setDate(5, Date.valueOf(LocalDate.now()));

                statement.executeUpdate();
            }
            connection.commit();
        }
    }
}
