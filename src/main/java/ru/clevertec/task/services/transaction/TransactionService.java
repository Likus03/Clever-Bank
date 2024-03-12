package ru.clevertec.task.services.transaction;

import ru.clevertec.task.enums.Currency;

import java.math.BigDecimal;

public interface TransactionService {
    boolean refillTransaction(String iban, BigDecimal amount, Currency currency);

    boolean withdrawalsTransaction(String iban, BigDecimal amount, Currency currency);

    boolean transferTransaction(String senderIban, BigDecimal amount, Currency currency, String recipientIban);
}
