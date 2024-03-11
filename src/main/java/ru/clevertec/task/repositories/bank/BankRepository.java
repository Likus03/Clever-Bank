package ru.clevertec.task.repositories.bank;

import ru.clevertec.task.entities.Bank;

import java.util.List;

public interface BankRepository {
    List<Bank> getBanks();
}
