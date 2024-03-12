package ru.clevertec.task.controllers.transaction;

import ru.clevertec.task.enums.Currency;
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
import static ru.clevertec.task.utils.Constants.*;

@WebServlet(TRANSFER_URL)
public class TransferTransactionalController extends HttpServlet {
    private final TransactionService transactionService = TransactionServiceImpl.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Currency> currencies = getCurrencyList();
        req.setAttribute(CURRENCIES, currencies);

        req.getRequestDispatcher(TRANSFER_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String senderIban = req.getParameter(SENDER_IBAN);
        Currency currency = Currency.valueOf(req.getParameter(CURRENCY));
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter(AMOUNT)));
        String recipientIban = req.getParameter(RECIPIENT_IBAN);

        if (transactionService.transferTransaction(senderIban, amount, currency, recipientIban)) {
            req.getRequestDispatcher(MENU).forward(req, resp);
        }
        req.getRequestDispatcher(ERROR_OCCURRED_PAGE).forward(req, resp);
    }
}
