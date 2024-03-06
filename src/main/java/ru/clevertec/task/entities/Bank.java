package ru.clevertec.task.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class Bank {
    private UUID id;
    private String name;
}
