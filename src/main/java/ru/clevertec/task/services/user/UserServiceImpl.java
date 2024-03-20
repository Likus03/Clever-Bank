package ru.clevertec.task.services.user;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.repositories.user.UserRepository;
import ru.clevertec.task.repositories.user.UserRepositoryImpl;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    private static volatile UserService userService;
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (userService == null) {
            synchronized (UserServiceImpl.class) {
                if (userService == null) {
                    userService = new UserServiceImpl();
                }
            }
        }
        return userService;
    }

    @Log
    @Override
    public UUID createUser(String login, String password, String phoneNumber, String firstname, String surname) {
        return userRepository.createUser(login, password, phoneNumber, firstname, surname);
    }

    @Log
    @Override
    public UUID getUser(String login, String password) {
        return userRepository.getUser(login, password);
    }
}