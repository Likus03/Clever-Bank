package ru.clevertec.task.services.user;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.entities.User;
import ru.clevertec.task.repositories.user.UserRepository;
import ru.clevertec.task.repositories.user.UserRepositoryImpl;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    private UserServiceImpl() {
    }

    private static class Holder {
        private static final UserService INSTANCE = new UserServiceImpl();
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    @Log
    @Override
    public UUID createUser(User user) {
        return userRepository.createUser(user);
    }

    @Log
    @Override
    public UUID getUser(User user) {
        return userRepository.getUser(user);
    }
}