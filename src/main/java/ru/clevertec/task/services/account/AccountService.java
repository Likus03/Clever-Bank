package ru.clevertec.task.services.account;

import ru.clevertec.task.entities.Account;
import ru.clevertec.task.enums.Currency;

import java.util.UUID;

public interface AccountService {
    Boolean createAccount(Account account);
}
