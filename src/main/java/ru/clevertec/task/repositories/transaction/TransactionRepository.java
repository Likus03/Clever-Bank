package ru.clevertec.task.repositories.transaction;

import ru.clevertec.task.entities.Transaction;

import java.sql.SQLException;

public interface TransactionRepository {
    void transaction(Transaction transaction) throws SQLException;
    void transferTransaction(Transaction transactionWithdrawals, Transaction transactionDeposit) throws SQLException;
}
