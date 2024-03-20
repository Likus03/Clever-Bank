package ru.clevertec.task.mappers;

import ru.clevertec.task.entities.Account;
import ru.clevertec.task.enums.Currency;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static java.math.BigDecimal.*;
import static ru.clevertec.task.utils.Constants.*;

public class AccountMapper {
    private AccountMapper() {
    }

    private static class Holder {
        private static final AccountMapper INSTANCE = new AccountMapper();
    }

    public static AccountMapper getInstance() {
        return Holder.INSTANCE;
    }

    public Account buildAccount(HttpServletRequest req) {
        return Account.builder()
                .iban(req.getParameter(IBAN))
                .bankId(UUID.fromString(req.getParameter(BANK_ID)))
                .userId((UUID) (req.getSession().getAttribute(USER_ID)))
                .balance(getBalance(req))
                .currency(Currency.valueOf(req.getParameter(CURRENCY)))
                .openingDate(getOpeningDate(req))
                .build();
    }

    private static BigDecimal getBalance(HttpServletRequest req) {
        String balance = req.getParameter(BALANCE);
        if (balance != null) {
            return new BigDecimal(balance);
        } else {
            return ZERO;
        }
    }

    private static LocalDate getOpeningDate(HttpServletRequest req) {
        String openingDate = req.getParameter(OPENING_DATE);
        if (openingDate != null) {
            return LocalDate.parse(openingDate);
        } else {
            return LocalDate.now();
        }
    }
}
