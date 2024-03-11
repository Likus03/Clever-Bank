package ru.clevertec.task.repositories.user;

import java.sql.SQLException;
import java.util.UUID;

public interface UserRepository {
    UUID createUser(String login, String password, String phoneNumber, String firstname, String surname);

    UUID getUser(String login, String password);
}
