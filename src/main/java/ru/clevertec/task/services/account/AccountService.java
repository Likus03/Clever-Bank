package ru.clevertec.task.services.account;

import java.util.UUID;

public interface AccountService {
    void createAccount(UUID bankId, UUID userId, String currency);
}
