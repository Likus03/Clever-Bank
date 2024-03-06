package ru.clevertec.task.entities;

import ru.clevertec.task.enums.Currency;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Count {
    private UUID id;
    private String iban;
    private UUID bankId;
    private UUID userId;
    private BigDecimal balance;
    private Currency currency;

    public Count(UUID id, String iban, UUID bankId, UUID userId, BigDecimal balance, Currency currency) {
        this.id = id;
        this.iban = iban;
        this.bankId = bankId;
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public UUID getBankId() {
        return bankId;
    }

    public void setBankId(UUID bankId) {
        this.bankId = bankId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Count count)) return false;
        return Objects.equals(getId(), count.getId()) && Objects.equals(getIban(), count.getIban()) && Objects.equals(getBankId(), count.getBankId()) && Objects.equals(getUserId(), count.getUserId()) && Objects.equals(getBalance(), count.getBalance()) && getCurrency() == count.getCurrency();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIban(), getBankId(), getUserId(), getBalance(), getCurrency());
    }
}
