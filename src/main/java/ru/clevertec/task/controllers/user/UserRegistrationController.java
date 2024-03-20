package ru.clevertec.task.controllers.user;

import ru.clevertec.task.entities.User;
import ru.clevertec.task.mappers.UserMapper;
import ru.clevertec.task.services.user.UserService;
import ru.clevertec.task.services.user.UserServiceImpl;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.concurrent.TimeUnit.SECONDS;
import static ru.clevertec.task.utils.Constants.*;

@WebServlet(value = REGISTRATION_URL, asyncSupported = true)
public class UserRegistrationController extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();
    private final UserMapper mapper = UserMapper.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = mapper.buildUser(req);

        AsyncContext asyncContext = req.startAsync();
        asyncContext.start(() -> {
            try {
                createUser(req, resp, user);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                asyncContext.complete();
            }
        });
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp, User user) throws InterruptedException, ExecutionException, TimeoutException, ServletException, IOException {
        UUID userId = supplyAsync(
                () -> userService.createUser(user))
                .get(30, SECONDS);

        userCreationProcession(req, resp, userId);
    }

    private static void userCreationProcession(HttpServletRequest req, HttpServletResponse resp, UUID userId) throws ServletException, IOException {
        if (userId != null) {
            req.getSession(true).setAttribute(USER_ID, userId);
            req.getRequestDispatcher(ACCOUNT_CREATE_PAGE).forward(req, resp);
        } else {
            req.getRequestDispatcher(REGISTRATION_FAILED_PAGE).forward(req, resp);
        }
    }
}
