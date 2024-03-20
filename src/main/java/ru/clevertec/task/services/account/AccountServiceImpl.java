package ru.clevertec.task.services.account;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.repositories.account.AccountRepository;
import ru.clevertec.task.repositories.account.AccountRepositoryImpl;

import java.sql.SQLException;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    private static volatile AccountService accountService;
    private static final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

    private AccountServiceImpl() {
    }

    public static AccountService getInstance() {
        if (accountService == null) {
            synchronized (AccountServiceImpl.class) {
                if (accountService == null) {
                    accountService = new AccountServiceImpl();
                }
            }
        }
        return accountService;
    }

    @Log
    @Override
    public Boolean createAccount(UUID bankId, UUID userId, Currency currency) {
        try {
            accountRepository.createAccount(generateIban(), bankId, userId, currency);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String generateIban() {
        return UUID.randomUUID().toString().replace("-", " ");
    }
}
