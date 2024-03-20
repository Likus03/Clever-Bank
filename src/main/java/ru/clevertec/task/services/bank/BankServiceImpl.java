package ru.clevertec.task.services.bank;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.entities.Bank;
import ru.clevertec.task.repositories.bank.BankRepository;
import ru.clevertec.task.repositories.bank.BankRepositoryImpl;
import ru.clevertec.task.services.account.AccountServiceImpl;

import java.util.List;

public class BankServiceImpl implements BankService {
    private static volatile BankService bankService;
    private final BankRepository bankRepository = BankRepositoryImpl.getInstance();

    private BankServiceImpl() {
    }

    public static BankService getInstance() {
        if (bankService == null) {
            synchronized (BankServiceImpl.class) {
                if (bankService == null) {
                    bankService = new BankServiceImpl();
                }
            }
        }
        return bankService;
    }

    @Log
    @Override
    public List<Bank> getBanks() {
        return bankRepository.getBanks();
    }
}
