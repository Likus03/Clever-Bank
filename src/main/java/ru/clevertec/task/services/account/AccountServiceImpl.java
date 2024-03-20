package ru.clevertec.task.services.account;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.entities.Account;
import ru.clevertec.task.repositories.account.AccountRepository;
import ru.clevertec.task.repositories.account.AccountRepositoryImpl;

import java.sql.SQLException;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    private static final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

    private AccountServiceImpl() {
    }

    private static class Holder {
        private static final AccountService INSTANCE = new AccountServiceImpl();
    }

    public static AccountService getInstance() {
        return Holder.INSTANCE;
    }
    @Log
    @Override
    public Boolean createAccount(Account account) {
        account.setIban(generateIban());
        try {
            accountRepository.createAccount(account);
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
