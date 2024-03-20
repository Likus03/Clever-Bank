package ru.clevertec.task.controllers.account;

import ru.clevertec.task.entities.Bank;
import ru.clevertec.task.enums.Currency;
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
import java.util.UUID;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = (UUID) req.getSession().getAttribute(USER_ID);
        UUID bankId = UUID.fromString(req.getParameter(BANK_ID));
        Currency currency = Currency.valueOf(req.getParameter(CURRENCY));

        AsyncContext asyncContext = req.startAsync();
        asyncContext.start(() -> {
            try {
                accountCreationRequest(req, resp, userId, bankId, currency);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
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

    private void accountCreationRequest(HttpServletRequest req, HttpServletResponse resp, UUID userId, UUID bankId, Currency currency) throws ExecutionException, InterruptedException, TimeoutException {
        Boolean isCompleted = supplyAsync(
                () -> accountService.createAccount(bankId, userId, currency))
                .get(30, SECONDS);

        handleAccountCreationResult(req, resp, isCompleted);
    }

    private static void handleAccountCreationResult(HttpServletRequest req, HttpServletResponse resp, Boolean isCompleted) {
        if (isCompleted) {
            try {
                req.getRequestDispatcher(MENU).forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                req.getRequestDispatcher(ERROR_OCCURRED_PAGE).forward(req, resp);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}