package ru.clevertec.task.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.task.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Account {
    private UUID id;
    private String iban;
    private UUID bankId;
    private UUID userId;
    private BigDecimal balance;
    private Currency currency;
    private LocalDate openingDate;
}
