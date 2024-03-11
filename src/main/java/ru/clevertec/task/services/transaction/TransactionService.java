package ru.clevertec.task.services.transaction;

import java.math.BigDecimal;

public interface TransactionService {
    boolean refillTransaction(String iban, BigDecimal amount, String currency);
}
