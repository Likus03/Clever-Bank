package ru.clevertec.task.services.account;

import ru.clevertec.task.repositories.account.AccountRepository;
import ru.clevertec.task.repositories.account.AccountRepositoryImpl;

import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    private static AccountService accountService;
    private static final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

    private AccountServiceImpl() {
    }

    public static AccountService getInstance() {
        if (accountService == null) {
            accountService = new AccountServiceImpl();
        }
        return accountService;
    }

    @Override
    public void createAccount(UUID bankId, UUID userId, String currency) {
        String iban = generateIban();
        accountRepository.createAccount(iban, bankId, userId, currency);
    }

    private String generateIban(){
        return UUID.randomUUID().toString().replace("-", " ");
    }
}
