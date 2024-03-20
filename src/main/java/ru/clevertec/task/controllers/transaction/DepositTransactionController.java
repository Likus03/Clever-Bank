package ru.clevertec.task.controllers.transaction;

import ru.clevertec.task.entities.Transaction;
import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.mappers.TransactionMapper;
import ru.clevertec.task.services.transaction.TransactionService;
import ru.clevertec.task.services.transaction.TransactionServiceImpl;

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
import static ru.clevertec.task.enums.TransactionType.DEPOSIT;
import static ru.clevertec.task.utils.Constants.*;

@WebServlet(value = ACCOUNT_DEPOSIT_URL, asyncSupported = true)
public class DepositTransactionController extends HttpServlet {
    private final TransactionService transactionService = TransactionServiceImpl.getInstance();
    private final TransactionMapper transactionMapper = TransactionMapper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Currency> currencies = getCurrencyList();
        req.setAttribute(CURRENCIES, currencies);

        req.getRequestDispatcher(ACCOUNT_REFILL_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String iban = req.getParameter(IBAN);
        Transaction transaction = transactionMapper.buildTransaction(req, DEPOSIT, iban);

        AsyncContext asyncContext = req.startAsync();
        asyncContext.start(() -> {
            try {
                depositTransactionRequest(req, resp, transaction);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                asyncContext.complete();
            }
        });
    }

    private void depositTransactionRequest(HttpServletRequest req, HttpServletResponse resp, Transaction transaction) throws ExecutionException, InterruptedException, TimeoutException, ServletException, IOException {
        Boolean isCompleted = supplyAsync(
                () -> transactionService.depositeTransaction(transaction))
                .get(30, SECONDS);

        handleDepositTransactionResult(req, resp, isCompleted);
    }

    private static void handleDepositTransactionResult(HttpServletRequest req, HttpServletResponse resp, Boolean isCompleted) throws ServletException, IOException {
        if (isCompleted) {
            req.getRequestDispatcher(MENU).forward(req, resp);
        } else {
            req.getRequestDispatcher(ERROR_OCCURRED_PAGE).forward(req, resp);
        }
    }
}
