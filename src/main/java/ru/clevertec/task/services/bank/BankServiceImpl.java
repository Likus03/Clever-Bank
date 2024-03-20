package ru.clevertec.task.services.bank;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.entities.Bank;
import ru.clevertec.task.mappers.UserMapper;
import ru.clevertec.task.repositories.bank.BankRepository;
import ru.clevertec.task.repositories.bank.BankRepositoryImpl;
import ru.clevertec.task.services.account.AccountServiceImpl;

import java.util.Collections;
import java.util.List;

public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository = BankRepositoryImpl.getInstance();

    private BankServiceImpl() {
    }

    private static class Holder {
        private static final BankService INSTANCE = new BankServiceImpl();
    }

    public static BankService getInstance() {
        return Holder.INSTANCE;
    }

    @Log
    @Override
    public List<Bank> getBanks() {
        return bankRepository.getBanks();
    }
}
