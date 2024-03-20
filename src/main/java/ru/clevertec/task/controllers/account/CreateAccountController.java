package ru.clevertec.task.controllers.account;

import ru.clevertec.task.entities.Account;
import ru.clevertec.task.entities.Bank;
import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.mappers.AccountMapper;
import ru.clevertec.task.services.account.AccountService;
import ru.clevertec.task.services.account.AccountServiceImpl;
import ru.clevertec.task.services.bank.BankService;
import ru.clevertec.task.services.bank.BankServiceImpl;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.SECONDS;
import static ru.clevertec.task.enums.Currency.getCurrencyList;
import static ru.clevertec.task.utils.Constants.*;

@WebServlet(value = ACCOUNT_CREATE_URL, asyncSupported = true)
public class CreateAccountController extends HttpServlet {
    private final BankService bankService = BankServiceImpl.getInstance();
    private final AccountService accountService = AccountServiceImpl.getInstance();
    private final AccountMapper accountMapper = AccountMapper.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = accountMapper.buildAccount(req);

        AsyncContext asyncContext = req.startAsync();
        asyncContext.start(() -> {
            try {
                accountCreationRequest(req, resp, account);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                asyncContext.complete();
            }
        });
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Bank> banks = bankService.getBanks();
        List<Currency> currencies = getCurrencyList();

        req.setAttribute(BANKS, banks);
        req.setAttribute(CURRENCIES, currencies);
        req.getRequestDispatcher(ACCOUNT_CREATE_PAGE).forward(req, resp);
    }

    private void accountCreationRequest(HttpServletRequest req, HttpServletResponse resp, Account account) throws ExecutionException, InterruptedException, TimeoutException, ServletException, IOException {
        Boolean isCompleted = supplyAsync(
                () -> accountService.createAccount(account))
                .get(30, SECONDS);

        handleAccountCreationResult(req, resp, isCompleted);
    }

    private static void handleAccountCreationResult(HttpServletRequest req, HttpServletResponse resp, Boolean isCompleted) throws ServletException, IOException {
        if (isCompleted) {
            req.getRequestDispatcher(MENU).forward(req, resp);
        } else {
            req.getRequestDispatcher(ERROR_OCCURRED_PAGE).forward(req, resp);
        }
    }
}