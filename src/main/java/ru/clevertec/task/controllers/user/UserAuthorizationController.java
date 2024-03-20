package ru.clevertec.task.controllers.user;

import ru.clevertec.task.services.user.UserService;
import ru.clevertec.task.services.user.UserServiceImpl;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.*;
import static ru.clevertec.task.utils.Constants.*;

@WebServlet(value = AUTHORIZATION_URL, asyncSupported = true)
public class UserAuthorizationController extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();
    long startTime;
    long endTime;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(START_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);

        AsyncContext asyncContext = req.startAsync();
        asyncContext.start(() -> {
            try {
                validateUser(req, resp, login, password);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                asyncContext.complete();
            }
        });
    }

    private void validateUser(HttpServletRequest req, HttpServletResponse resp, String login, String password) throws ServletException, IOException, ExecutionException, InterruptedException, TimeoutException {
        UUID userId = supplyAsync(
                () -> userService.getUser(login, password))
                .get(30, SECONDS);

        if (userId != null) {
            req.getSession(true).setAttribute(USER_ID, userId);
            req.getRequestDispatcher(MENU).forward(req, resp);
        } else {
            req.getRequestDispatcher(LOGIN_FAILED_PAGE).forward(req, resp);
        }
    }
}
