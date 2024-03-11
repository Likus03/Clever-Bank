package ru.clevertec.task.repositories.account;

import java.sql.SQLException;
import java.util.UUID;

public interface AccountRepository {
    void createAccount(String iban, UUID bankId, UUID userId, String currency) throws SQLException;
}
