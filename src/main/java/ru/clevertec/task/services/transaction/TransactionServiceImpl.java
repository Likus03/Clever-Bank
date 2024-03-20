package ru.clevertec.task.services.transaction;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.entities.Transaction;
import ru.clevertec.task.repositories.transaction.TransactionRepository;
import ru.clevertec.task.repositories.transaction.TransactionRepositoryImpl;

import java.sql.SQLException;
import java.util.UUID;

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository = TransactionRepositoryImpl.getInstance();

    private TransactionServiceImpl() {
    }

    private static class Holder {
        private static final TransactionService INSTANCE = new TransactionServiceImpl();
    }

    public static TransactionService getInstance() {
        return Holder.INSTANCE;
    }

    @Log
    @Override
    public boolean depositeTransaction(Transaction transaction) {
        try {
            transactionRepository.transaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Log
    @Override
    public boolean withdrawalsTransaction(Transaction transaction) {
        transaction.setAmount(transaction.getAmount().negate());
        try {
            transactionRepository.transaction(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Log
    @Override
    public boolean transferTransaction(Transaction transactionWithdrawals, Transaction transactionDeposit) {
        transactionWithdrawals.setAmount(transactionWithdrawals.getAmount().negate());

        UUID transactionId = UUID.randomUUID();
        transactionWithdrawals.setTransactionId(transactionId);
        transactionDeposit.setTransactionId(transactionId);

        try {
            transactionRepository.transferTransaction(transactionWithdrawals, transactionDeposit);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
