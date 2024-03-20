package ru.clevertec.task.repositories.account;

import ru.clevertec.task.db.DbConnection;
import ru.clevertec.task.enums.Currency;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import static java.sql.Types.OTHER;

public class AccountRepositoryImpl implements AccountRepository {
    private static volatile AccountRepository accountRepository;

    private AccountRepositoryImpl() {
    }

    public static AccountRepository getInstance() {
        if (accountRepository == null) {
            synchronized (AccountRepositoryImpl.class) {
                if (accountRepository == null) {
                    accountRepository = new AccountRepositoryImpl();
                }
            }
        }
        return accountRepository;
    }

    @Override
    public void createAccount(String iban, UUID bankId, UUID userId, Currency currency) throws SQLException {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);
            String query = "INSERT INTO account(iban, bank_id, user_id, currency, opening_date) VALUES (?,?,?,?,?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, iban);
                statement.setObject(2, bankId);
                statement.setObject(3, userId);
                statement.setObject(4, currency, OTHER);
                statement.setDate(5, Date.valueOf(LocalDate.now()));

                statement.executeUpdate();
            }
            connection.commit();
        }
    }
}
