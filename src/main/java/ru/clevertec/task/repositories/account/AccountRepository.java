package ru.clevertec.task.repositories.account;

import ru.clevertec.task.entities.Account;

import java.sql.SQLException;

public interface AccountRepository {
    void createAccount(Account account) throws SQLException;
}
