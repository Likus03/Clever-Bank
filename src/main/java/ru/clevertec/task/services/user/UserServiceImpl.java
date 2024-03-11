package ru.clevertec.task.services.user;

import lombok.SneakyThrows;
import ru.clevertec.task.repositories.user.UserRepository;
import ru.clevertec.task.repositories.user.UserRepositoryImpl;

import java.sql.SQLException;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private static UserService userService;
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    @Override
    public UUID createUser(String login, String password, String phoneNumber, String firstname, String surname) throws SQLException {
        return userRepository.createUser(login, password, phoneNumber, firstname, surname);
    }

    @Override
    public UUID getUser(String login, String password) throws SQLException {
        return userRepository.getUser(login, password);
    }
}