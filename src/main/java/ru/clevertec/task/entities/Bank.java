package ru.clevertec.task.entities;

import java.util.Objects;
import java.util.UUID;

public class Bank {
    private UUID id;
    private String name;

    public Bank(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank bank)) return false;
        return Objects.equals(getId(), bank.getId()) && Objects.equals(getName(), bank.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
