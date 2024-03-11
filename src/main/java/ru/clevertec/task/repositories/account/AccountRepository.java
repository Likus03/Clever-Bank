package ru.clevertec.task.repositories.account;

import java.util.UUID;

public interface AccountRepository {
    void createAccount(String iban, UUID bankId, UUID userId, String currency);
}
