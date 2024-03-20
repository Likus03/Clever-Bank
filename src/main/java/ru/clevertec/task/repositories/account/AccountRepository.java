package ru.clevertec.task.repositories.account;

import ru.clevertec.task.enums.Currency;

import java.sql.SQLException;
import java.util.UUID;

public interface AccountRepository {
    void createAccount(String iban, UUID bankId, UUID userId, Currency currency) throws SQLException;
}
