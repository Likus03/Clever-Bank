package ru.clevertec.task.controllers.transaction;

import ru.clevertec.task.entities.Transaction;
import ru.clevertec.task.enums.Currency;
import ru.clevertec.task.mappers.TransactionMapper;
import ru.clevertec.task.services.transaction.TransactionService;
import ru.clevertec.task.services.transaction.TransactionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static ru.clevertec.task.enums.Currency.getCurrencyList;
import static ru.clevertec.task.enums.TransactionType.DEPOSIT;
import static ru.clevertec.task.enums.TransactionType.WITHDRAWALS;
import static ru.clevertec.task.utils.Constants.*;

@WebServlet(value = ACCOUNT_WITHDRAWALS_URL, asyncSupported = true)
public class WithdrawalTransactionController extends HttpServlet {
    private final TransactionService transactionService = TransactionServiceImpl.getInstance();
    private final TransactionMapper transactionMapper = TransactionMapper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Currency> currencies = getCurrencyList();
        req.setAttribute(CURRENCIES, currencies);

        req.getRequestDispatcher(ACCOUNT_WITHDRAWALS_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String iban = req.getParameter(IBAN);
        Transaction transaction = transactionMapper.buildTransaction(req, WITHDRAWALS, iban);
        if (transactionService.withdrawalsTransaction(transaction)) {
            req.getRequestDispatcher(MENU).forward(req, resp);
        }
        req.getRequestDispatcher(ERROR_OCCURRED_PAGE).forward(req, resp);
    }
}
