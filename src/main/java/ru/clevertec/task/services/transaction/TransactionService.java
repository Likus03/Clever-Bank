package ru.clevertec.task.services.transaction;

import ru.clevertec.task.entities.Transaction;

public interface TransactionService {
    boolean depositeTransaction(Transaction transaction);

    boolean withdrawalsTransaction(Transaction transaction);

    boolean transferTransaction(Transaction transactionWithdrawals, Transaction transactionDeposit);
}
