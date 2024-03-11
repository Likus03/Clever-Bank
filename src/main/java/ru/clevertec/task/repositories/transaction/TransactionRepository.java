package ru.clevertec.task.repositories.transaction;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public interface TransactionRepository {
    void refillTransaction(BigDecimal amount, String transactionType, String iban, LocalDate date, LocalTime time, String currency) throws SQLException;
}
