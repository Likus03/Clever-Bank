package ru.clevertec.task.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@Data
public class Transaction {
    private UUID id;
    private BigDecimal amount;
    private TransactionType transactionType;
    private String senderAccount;
    private String recipientAccount;
    private LocalDate date;
    private LocalTime time;
    private Currency currency;
}
