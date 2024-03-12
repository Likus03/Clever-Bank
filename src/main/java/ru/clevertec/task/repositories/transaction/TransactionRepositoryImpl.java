package ru.clevertec.task.repositories.transaction;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.db.DbConnection;
import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.enums.TransactionType;
import ru.clevertec.task.utils.Rates;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static java.sql.Types.OTHER;

public class TransactionRepositoryImpl implements TransactionRepository {
    private static TransactionRepository transactionRepository;

    private TransactionRepositoryImpl() {
    }

    public static TransactionRepository getInstance() {
        if (transactionRepository == null) {
            transactionRepository = new TransactionRepositoryImpl();
        }
        return transactionRepository;
    }

    @Log
    @Override
    public void refillTransaction(BigDecimal amount, TransactionType transactionType, String iban, LocalDate date, LocalTime time, Currency currency) throws SQLException {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            Currency accountCurrency = getCurrency(iban, connection);
            BigDecimal rate = Rates.getExchangeRates(currency, accountCurrency);

            if (updateAccountBalanceForRefill(amount.multiply(rate).setScale(2, RoundingMode.HALF_EVEN), iban, connection)) {
                insertRefillTransactionQuery(amount, transactionType, iban, date, time, currency, connection);
            } else {
                connection.rollback();
                throw new SQLException("The transaction was rolled back");
            }
            connection.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void withdrawalTransaction(BigDecimal amount, TransactionType transactionType, String iban, LocalDate date, LocalTime time, Currency currency) throws SQLException {
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            Currency accountCurrency = getCurrency(iban, connection);
            BigDecimal rate = Rates.getExchangeRates(currency, accountCurrency);

            if (updateAccountBalanceForWithdrawal(amount.multiply(rate).setScale(2, RoundingMode.HALF_EVEN), iban, connection)) {
                insertWithdrawalTransactionQuery(amount, transactionType, iban, date, time, currency, connection);
            } else {
                connection.rollback();
                throw new SQLException("The transaction was rolled back");
            }
            connection.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Log
    private Currency getCurrency(String iban, Connection connection) throws SQLException {
        String selectQuery = "SELECT currency FROM account WHERE iban=?";

        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, iban);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Currency.valueOf(resultSet.getString("currency"));
                }
            }
        }
        return null;
    }

    private static void insertRefillTransactionQuery(BigDecimal amount, TransactionType transactionType, String iban, LocalDate date, LocalTime time, Currency currency, Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO transaction(amount, transaction_type, recipient_account_iban, date, time, currency) VALUES (?,?,?,?,?,?)";

        insertTransactionRecord(amount, transactionType, iban, date, time, currency, connection, insertQuery);
    }

    private static void insertWithdrawalTransactionQuery(BigDecimal amount, TransactionType transactionType, String iban, LocalDate date, LocalTime time, Currency currency, Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO transaction(amount, transaction_type, sender_account_iban, date, time, currency) VALUES (?,?,?,?,?,?)";

        insertTransactionRecord(amount, transactionType, iban, date, time, currency, connection, insertQuery);
    }

    private static void insertTransactionRecord(BigDecimal amount, TransactionType transactionType, String iban, LocalDate date, LocalTime time, Currency currency, Connection connection, String insertQuery) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setBigDecimal(1, amount);
            statement.setObject(2, transactionType, OTHER);
            statement.setString(3, iban);
            statement.setDate(4, Date.valueOf(date));
            statement.setTime(5, Time.valueOf(time));
            statement.setObject(6, currency, OTHER);
            statement.executeUpdate();
        }
    }

    @Log
    private static boolean updateAccountBalanceForRefill(BigDecimal amount, String iban, Connection connection) {
        String updateQuery = "UPDATE account SET balance = balance + ? WHERE iban = ?";

        return updateAccountBalance(amount, iban, connection, updateQuery);
    }

    @Log
    private static boolean updateAccountBalanceForWithdrawal(BigDecimal amount, String iban, Connection connection) {
        String updateQuery = "UPDATE account SET balance = balance - ? WHERE iban = ?";

        return updateAccountBalance(amount, iban, connection, updateQuery);
    }

    private static boolean updateAccountBalance(BigDecimal amount, String iban, Connection connection, String updateQuery) {
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setBigDecimal(1, amount);
            statement.setString(2, iban);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
