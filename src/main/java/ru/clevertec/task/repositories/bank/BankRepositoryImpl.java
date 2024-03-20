package ru.clevertec.task.repositories.bank;

import ru.clevertec.task.db.DbConnection;
import ru.clevertec.task.entities.Bank;
import ru.clevertec.task.mappers.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static ru.clevertec.task.utils.Constants.ID;
import static ru.clevertec.task.utils.Constants.NAME;

public class BankRepositoryImpl implements BankRepository {
    private BankRepositoryImpl() {
    }

    private static class Holder {
        private static final BankRepository INSTANCE = new BankRepositoryImpl();
    }

    public static BankRepository getInstance() {
        return Holder.INSTANCE;
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
                        banks.add(new Bank((UUID) resultSet.getObject(ID), resultSet.getString(NAME)));
                    }
                    return banks;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

