package ru.clevertec.task.services.user;

import ru.clevertec.task.entities.User;

import java.util.UUID;

public interface UserService {
    UUID createUser(User user);
    UUID getUser(User user);
}
