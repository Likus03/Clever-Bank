package ru.clevertec.task.controllers.transaction;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.clevertec.task.services.transaction.TransactionService;
import ru.clevertec.task.services.transaction.TransactionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
        String currency = req.getParameter("currency");
        BigDecimal amount = BigDecimal.valueOf(Long.parseLong(req.getParameter("amount")));

        if (transactionService.refillTransaction(iban, amount, currency)) {
            req.getRequestDispatcher("/WEB-INF/views/other/menu.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("/WEB-INF/views/errors/error-occurred.jsp").forward(req, resp);
    }


}
