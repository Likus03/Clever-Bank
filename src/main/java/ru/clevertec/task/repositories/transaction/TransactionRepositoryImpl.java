package ru.clevertec.task.repositories.transaction;

import ru.clevertec.task.db.DbConnection;
import ru.clevertec.task.entities.Transaction;
import ru.clevertec.task.enums.Currency;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;
import static java.sql.Types.OTHER;
import static ru.clevertec.task.db.DbConnection.*;
import static ru.clevertec.task.utils.Constants.getAmount;
import static ru.clevertec.task.utils.Rates.getExchangeRates;

public class TransactionRepositoryImpl implements TransactionRepository {
    private TransactionRepositoryImpl() {
    }

    private static class Holder {
        private static final TransactionRepository INSTANCE = new TransactionRepositoryImpl();
    }

    public static TransactionRepository getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void transaction(Transaction transaction) throws SQLException {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
//            connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);

            Currency accountCurrency = getCurrency(transaction.getIban(), connection);
            BigDecimal rate = getExchangeRates(transaction.getCurrency(), accountCurrency);

            if (updateAccountBalance(getAmount(transaction.getAmount(), rate), transaction.getIban(), connection)) {
                insertTransactionQuery(transaction, connection);
            } else {
                connection.rollback();
                throw new SQLException("The transaction was rolled back");
            }
            connection.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void transferTransaction(Transaction transactionWithdrawals, Transaction transactionDeposit) throws SQLException {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);

            Currency accountCurrency = getCurrency(transactionWithdrawals.getIban(), connection);
            BigDecimal rate = getExchangeRates(transactionWithdrawals.getCurrency(), accountCurrency);

            final BigDecimal transferAmount = getAmount(transactionDeposit.getAmount(), rate);

            if (updateAccountBalance(transferAmount.negate(), transactionWithdrawals.getIban(), connection) &&
                    updateAccountBalance(transferAmount, transactionDeposit.getIban(), connection)) {
                insertTransferTransactionQuery(transactionWithdrawals, transactionDeposit, connection);
            } else {
                connection.rollback();
                throw new SQLException("The transaction was rolled back");
            }
            connection.commit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

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

    private static void insertTransactionQuery(Transaction transaction, Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO transaction(transaction_type, iban, amount, date, time, currency) VALUES (?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setObject(1, transaction.getTransactionType(), OTHER);
            statement.setString(2, transaction.getIban());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setDate(4, Date.valueOf(transaction.getDate()));
            statement.setTime(5, Time.valueOf(transaction.getTime()));
            statement.setObject(6, transaction.getCurrency(), OTHER);
            statement.executeUpdate();
        }
    }

    private static void insertTransferTransactionQuery(Transaction transactionWithdrawals, Transaction transactionDeposit, Connection connection) throws SQLException {
        String insertQuery = "INSERT INTO transaction (transaction_id, transaction_type, iban, amount, date, time, currency) " +
                "VALUES (?,?,?,?,?,?,?), (?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setObject(1, transactionWithdrawals.getTransactionId());
            statement.setObject(2, transactionWithdrawals.getTransactionType(), OTHER);
            statement.setString(3, transactionWithdrawals.getIban());
            statement.setBigDecimal(4, transactionWithdrawals.getAmount());
            statement.setDate(5, Date.valueOf(transactionWithdrawals.getDate()));
            statement.setTime(6, Time.valueOf(transactionWithdrawals.getTime()));
            statement.setObject(7, transactionWithdrawals.getCurrency(), OTHER);

            statement.setObject(8, transactionDeposit.getTransactionId());
            statement.setObject(9, transactionDeposit.getTransactionType(), OTHER);
            statement.setString(10, transactionDeposit.getIban());
            statement.setBigDecimal(11, transactionDeposit.getAmount());
            statement.setDate(12, Date.valueOf(transactionDeposit.getDate()));
            statement.setTime(13, Time.valueOf(transactionDeposit.getTime()));
            statement.setObject(14, transactionDeposit.getCurrency(), OTHER);
            statement.executeUpdate();
        }
    }

    synchronized private static boolean updateAccountBalance(BigDecimal amount, String iban, Connection connection) {
        String updateQuery = "UPDATE account SET balance = balance + ? WHERE iban = ?";

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