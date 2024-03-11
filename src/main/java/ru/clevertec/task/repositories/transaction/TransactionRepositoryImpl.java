package ru.clevertec.task.repositories.transaction;

import ru.clevertec.task.db.DbConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

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

    @Override
    public void refillTransaction(BigDecimal amount, String transactionType, String iban, LocalDate date, LocalTime time) throws SQLException{
        try (Connection connection = DbConnection.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            if (updateAccountBalance(amount, iban, connection)) {
                insertTransaction(amount, transactionType, iban, date, time, connection);
            } else {
                connection.rollback();
                throw new SQLException("The transaction was rolled back");
            }
            connection.commit();
        }
    }

    private static void insertTransaction(BigDecimal amount, String transactionType, String iban, LocalDate date, LocalTime time, Connection connection) throws SQLException{
        String insertQuery = "INSERT INTO transactional(amount, transaction_type, recipient_account_iban, date, time) VALUES (?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setBigDecimal(1, amount);
            statement.setString(2, transactionType);
            statement.setString(3, iban);
            statement.setDate(4, Date.valueOf(date));
            statement.setTime(5, Time.valueOf(time));
            statement.executeUpdate();
        }
    }

    private static boolean updateAccountBalance(BigDecimal amount, String iban, Connection connection){
        String updateQuery = "UPDATE account SET balance = balance + ? WHERE iban = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setBigDecimal(1, amount);
            statement.setObject(2, iban);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
