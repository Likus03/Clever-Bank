package ru.clevertec.task.entities;

import ru.clevertec.task.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private BigDecimal amount;
    private TransactionType transactionType;
    private String senderAccount;
    private String recipientAccount;
    private LocalDate date;
    private LocalTime time;

    public Transaction(UUID id, BigDecimal amount, TransactionType transactionType, String senderAccount, String recipientAccount, LocalDate date, LocalTime time) {
        this.id = id;
        this.amount = amount;
        this.transactionType = transactionType;
        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
        this.date = date;
        this.time = time;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(String recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getAmount(), that.getAmount()) && getTransactionType() == that.getTransactionType() && Objects.equals(getSenderAccount(), that.getSenderAccount()) && Objects.equals(getRecipientAccount(), that.getRecipientAccount()) && Objects.equals(getDate(), that.getDate()) && Objects.equals(getTime(), that.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAmount(), getTransactionType(), getSenderAccount(), getRecipientAccount(), getDate(), getTime());
    }
}
