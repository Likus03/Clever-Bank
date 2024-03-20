package ru.clevertec.task.repositories.account;

import ru.clevertec.task.db.DbConnection;
import ru.clevertec.task.entities.Account;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.sql.Types.OTHER;

public class AccountRepositoryImpl implements AccountRepository {
    private AccountRepositoryImpl() {
    }

    private static class Holder {
        private static final AccountRepository INSTANCE = new AccountRepositoryImpl();
    }

    public static AccountRepository getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void createAccount(Account account) throws SQLException {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);
            String query = "INSERT INTO account(iban, bank_id, user_id, currency, opening_date) VALUES (?,?,?,?,?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, account.getIban());
                statement.setObject(2, account.getBankId());
                statement.setObject(3, account.getUserId());
                statement.setObject(4, account.getCurrency(), OTHER);
                statement.setDate(5, Date.valueOf(account.getOpeningDate()));

                statement.executeUpdate();
            }
            connection.commit();
        }
    }
}
