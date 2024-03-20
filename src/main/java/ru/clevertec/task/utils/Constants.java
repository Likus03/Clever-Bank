package ru.clevertec.task.utils;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_EVEN;

public class Constants {
    public static final String CONFIG = "config.properties";
    public static final String DB_URL = "db.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DRIVER = "org.postgresql.Driver";

    public static final String MENU = "/WEB-INF/views/other/menu.jsp";
    public static final String ERROR_OCCURRED_PAGE = "/WEB-INF/views/errors/error-occurred.jsp";
    public static final String ACCOUNT_CREATE_PAGE = "/WEB-INF/views/account/createAccount.jsp";
    public static final String ACCOUNT_CREATE_URL = "/account/create";
    public static final String ACCOUNT_REFILL_URL = "/transaction/refill";
    public static final String ACCOUNT_REFILL_PAGE = "/WEB-INF/views/transaction/refill.jsp";
    public static final String AUTHORIZATION_URL = "/user/authorization";
    public static final String START_PAGE = "/index.jsp";
    public static final String LOGIN_FAILED_PAGE = "/WEB-INF/views/errors/login-failed.jsp";
    public static final String REGISTRATION_URL = "/user/registration";
    public static final String REGISTRATION_PAGE = "/WEB-INF/views/user/registration.jsp";
    public static final String REGISTRATION_FAILED_PAGE = "/WEB-INF/views/errors/registration-failed.jsp";
    public static final String JSON_OBJECT_NAME = "conversion_rates";
    public static final String ACCOUNT_WITHDRAWALS_URL = "/transaction/withdrawals";
    public static final String ACCOUNT_WITHDRAWALS_PAGE = "/WEB-INF/views/transaction/withdrawals.jsp";
    public static final String TRANSFER_URL = "/transaction/transfer";
    public static final String TRANSFER_PAGE = "/WEB-INF/views/transaction/transfer.jsp";

    public static final String USER_ID = "userId";
    public static final String BANK_ID = "bankId";
    public static final String CURRENCY = "currency";
    public static final String CURRENCIES = "currencies";
    public static final String BANKS = "banks";
    public static final String IBAN = "iban";
    public static final String SENDER_IBAN = "senderIban";
    public static final String RECIPIENT_IBAN = "recipientIban";
    public static final String AMOUNT = "amount";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String PHONENUMBER = "phoneNumber";
    public static final String FIRSTNAME = "firstname";
    public static final String SURNAME = "surname";
    public static final String NAME = "name";
    public static final String ID = "id";


    public static final String API_KEY = "bdd1af0bd03086e428c68bfb";  //will change


    public static BigDecimal getAmount(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).setScale(2, HALF_EVEN);
    }
}
