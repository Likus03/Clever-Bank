package ru.clevertec.task.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Transaction {
    private UUID id;
    private UUID transactionId;
    private TransactionType transactionType;
    private String iban;
    private BigDecimal amount;
    private LocalDate date;
    private LocalTime time;
    private Currency currency;
}
