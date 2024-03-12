package ru.clevertec.task.services.transaction;

import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.enums.TransactionType;
import ru.clevertec.task.repositories.transaction.TransactionRepository;
import ru.clevertec.task.repositories.transaction.TransactionRepositoryImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionServiceImpl implements TransactionService {
    private static TransactionService transactionService;
    private final TransactionRepository transactionRepository = TransactionRepositoryImpl.getInstance();

    private TransactionServiceImpl() {
    }

    public static TransactionService getInstance() {
        if (transactionService == null) {
            transactionService = new TransactionServiceImpl();
        }
        return transactionService;
    }

    @Override
    public boolean refillTransaction(String iban, BigDecimal amount, Currency currency) {
        try {
            transactionRepository.refillTransaction(amount, TransactionType.REFILL, iban, LocalDate.now(), LocalTime.now(), currency);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean withdrawalTransaction(String iban, BigDecimal amount, Currency currency) {
        try {
            transactionRepository.withdrawalTransaction(amount, TransactionType.WITHDRAWALS, iban, LocalDate.now(), LocalTime.now(), currency);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
