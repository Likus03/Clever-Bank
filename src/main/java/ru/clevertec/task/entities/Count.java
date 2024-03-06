package ru.clevertec.task.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.clevertec.task.enums.Currency;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Data
public class Count {
    private UUID id;
    private String iban;
    private UUID bankId;
    private UUID userId;
    private BigDecimal balance;
    private Currency currency;
}
