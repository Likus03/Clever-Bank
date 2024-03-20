package ru.clevertec.task.repositories.user;

import ru.clevertec.task.entities.User;

import java.util.UUID;

public interface UserRepository {
    UUID createUser(User user);

    UUID getUser(User user);
}
