package ru.clevertec.task.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class User {
    private UUID id;
    private String firstname;
    private String surname;
    private String phoneNumber;
    private String login;
    private String password;
}
