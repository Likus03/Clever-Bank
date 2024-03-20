package ru.clevertec.task.mappers;

import ru.clevertec.task.entities.Transaction;
import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.enums.TransactionType;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static ru.clevertec.task.utils.Constants.*;

public class TransactionMapper {
    private TransactionMapper() {
    }

    private static class Holder {
        private static final TransactionMapper INSTANCE = new TransactionMapper();
    }

    public static TransactionMapper getInstance() {
        return Holder.INSTANCE;
    }

    public Transaction buildTransaction(HttpServletRequest req, TransactionType transactionType, String iban) {
        return Transaction.builder()
                .transactionType(transactionType)
                .iban(iban)
                .amount(new BigDecimal(req.getParameter(AMOUNT)))
                .date(LocalDate.now())
                .time(LocalTime.now())
                .currency(Currency.valueOf(req.getParameter(CURRENCY)))
                .build();
    }
}
