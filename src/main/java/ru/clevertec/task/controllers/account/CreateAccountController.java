package ru.clevertec.task.controllers.account;

import ru.clevertec.task.entities.Bank;
import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.services.account.AccountService;
import ru.clevertec.task.services.account.AccountServiceImpl;
import ru.clevertec.task.services.bank.BankService;
import ru.clevertec.task.services.bank.BankServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static ru.clevertec.task.enums.Currency.*;
import static ru.clevertec.task.utils.Constants.*;

@WebServlet(ACCOUNT_CREATE_URL)
public class CreateAccountController extends HttpServlet {
    private final BankService bankService = BankServiceImpl.getInstance();
    private final AccountService accountService = AccountServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = (UUID) req.getSession().getAttribute(USER_ID);
        UUID bankId = UUID.fromString(req.getParameter(BANK_ID));
        String currency = req.getParameter(CURRENCY);

        if (accountService.createAccount(bankId, userId, currency)) {
            req.getRequestDispatcher(MENU).forward(req, resp);
        } else {
            req.getRequestDispatcher(ERROR_OCCURRED_PAGE).forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Bank> banks = bankService.getBanks();
        List<Currency> currencies = getCurrencyList();

        req.setAttribute(BANKS, banks);
        req.setAttribute(CURRENCIES, currencies);
        req.getRequestDispatcher(ACCOUNT_CREATE_PAGE).forward(req, resp);
    }
}