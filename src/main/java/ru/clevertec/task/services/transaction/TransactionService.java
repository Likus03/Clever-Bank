package ru.clevertec.task.services.transaction;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface TransactionService {
    boolean refillTransaction(String iban, BigDecimal amount);
}
