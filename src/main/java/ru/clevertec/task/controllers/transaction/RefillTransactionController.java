package ru.clevertec.task.controllers.transaction;

import ru.clevertec.task.services.transaction.TransactionService;
import ru.clevertec.task.services.transaction.TransactionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(urlPatterns = "/transaction/refill")
public class RefillTransactionController extends HttpServlet {
    private final TransactionService transactionService = TransactionServiceImpl.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/transaction/refill.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String iban = req.getParameter("iban");
        BigDecimal amount = BigDecimal.valueOf(Long.parseLong(req.getParameter("amount")));

        if (transactionService.refillTransaction(iban, amount)) {
            req.getRequestDispatcher("/WEB-INF/views/other/menu.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("/WEB-INF/views/errors/error-occurred.jsp").forward(req, resp);
    }
}
