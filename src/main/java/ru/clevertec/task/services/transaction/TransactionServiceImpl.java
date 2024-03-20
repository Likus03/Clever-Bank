package ru.clevertec.task.services.transaction;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.repositories.transaction.TransactionRepository;
import ru.clevertec.task.repositories.transaction.TransactionRepositoryImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static ru.clevertec.task.enums.TransactionType.*;

public class TransactionServiceImpl implements TransactionService {
    private static volatile TransactionService transactionService;
    private final TransactionRepository transactionRepository = TransactionRepositoryImpl.getInstance();

    private TransactionServiceImpl() {
    }

    public static TransactionService getInstance() {
        if (transactionService == null) {
            synchronized (TransactionServiceImpl.class) {
                if (transactionService == null) {
                    transactionService = new TransactionServiceImpl();
                }
            }
        }
        return transactionService;
    }

    @Log
    @Override
    public boolean refillTransaction(String iban, BigDecimal amount, Currency currency) {
        try {
            transactionRepository.refillTransaction(amount, REFILL, iban, LocalDate.now(), LocalTime.now(), currency);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Log
    @Override
    public boolean withdrawalsTransaction(String iban, BigDecimal amount, Currency currency) {
        try {
            transactionRepository.withdrawalsTransaction(amount, WITHDRAWALS, iban, LocalDate.now(), LocalTime.now(), currency);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Log
    @Override
    public boolean transferTransaction(String senderIban, BigDecimal amount, Currency currency, String recipientIban) {
        try {
            transactionRepository.transferTransaction(amount, TRANSFER, senderIban, recipientIban, LocalDate.now(), LocalTime.now(), currency);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
