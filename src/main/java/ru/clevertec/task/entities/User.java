package ru.clevertec.task.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class User {
    private UUID id;
    private String firstname;
    private String surname;
    private String phoneNumber;
    private String login;
    private String password;
}
