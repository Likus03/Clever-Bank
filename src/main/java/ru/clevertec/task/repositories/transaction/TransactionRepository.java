package ru.clevertec.task.repositories.transaction;

import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.enums.TransactionType;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public interface TransactionRepository {
    void refillTransaction(BigDecimal amount, TransactionType transactionType, String iban, LocalDate date, LocalTime time, Currency currency) throws SQLException;

    void withdrawalsTransaction(BigDecimal amount, TransactionType transactionType, String iban, LocalDate date, LocalTime time, Currency currency) throws SQLException;

    void transferTransaction(BigDecimal amount, TransactionType transactionType, String senderIban, String recipientIban, LocalDate date, LocalTime time, Currency currency) throws SQLException;
}
