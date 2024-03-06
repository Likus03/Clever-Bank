package ru.clevertec.task.entities;

import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID id;
    private String firstname;
    private String surname;
    private String phoneNumber;

    public User(UUID id, String firstname, String surname, String phoneNumber) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getFirstname(), user.getFirstname()) && Objects.equals(getSurname(), user.getSurname()) && Objects.equals(getPhoneNumber(), user.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstname(), getSurname(), getPhoneNumber());
    }
}
