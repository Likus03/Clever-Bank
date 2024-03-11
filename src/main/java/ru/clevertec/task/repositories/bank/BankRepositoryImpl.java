package ru.clevertec.task.repositories.bank;

import ru.clevertec.task.db.DbConnection;
import ru.clevertec.task.entities.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankRepositoryImpl implements BankRepository {
    private static BankRepository bankRepository;

    private BankRepositoryImpl() {
    }

    public static BankRepository getInstance() {
        if (bankRepository == null) {
            bankRepository = new BankRepositoryImpl();
        }
        return bankRepository;
    }

    @Override
    public List<Bank> getBanks() {
        List<Bank> banks = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection()) {
            connection.setReadOnly(true);

            String query = "SELECT * FROM bank";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        banks.add(new Bank((UUID) resultSet.getObject("id"), resultSet.getString("name")));
                    }
                    return banks;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

