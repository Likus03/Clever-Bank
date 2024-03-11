package ru.clevertec.task.services.bank;

import ru.clevertec.task.entities.Bank;
import ru.clevertec.task.repositories.bank.BankRepository;
import ru.clevertec.task.repositories.bank.BankRepositoryImpl;

import java.util.List;

public class BankServiceImpl implements BankService{
    private static BankService bankService;
    private final BankRepository bankRepository = BankRepositoryImpl.getInstance();
    private BankServiceImpl(){}
    public static BankService getInstance() {
        if (bankService == null) {
            bankService = new BankServiceImpl();
        }
        return bankService;
    }

    @Override
    public List<Bank> getBanks() {
        return bankRepository.getBanks();
    }
}
