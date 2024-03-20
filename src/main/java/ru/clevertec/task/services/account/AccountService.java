package ru.clevertec.task.services.account;

import ru.clevertec.task.enums.Currency;

import java.util.UUID;

public interface AccountService {
    Boolean createAccount(UUID bankId, UUID userId, Currency currency);
}
