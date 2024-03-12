package ru.clevertec.task.controllers.user;

import ru.clevertec.task.aspects.Log;
import ru.clevertec.task.services.user.UserService;
import ru.clevertec.task.services.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

import static ru.clevertec.task.utils.Constants.*;

@WebServlet(AUTHORIZATION_URL)
public class UserAuthorizationController extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(START_PAGE).forward(req, resp);
    }
    @Log
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);

        validateUser(req, resp, login, password);
    }

    private void validateUser(HttpServletRequest req, HttpServletResponse resp, String login, String password) throws ServletException, IOException {
        UUID userId = userService.getUser(login, password);

        if (userId != null) {
            HttpSession session = req.getSession();
            session.setAttribute(USER_ID, userId);

            goToCreateAccountPage(req, resp);
        } else {
            goToErrorPage(req, resp);
        }

    }

    private static void goToErrorPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN_FAILED_PAGE).forward(req, resp);
    }

    private static void goToCreateAccountPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(MENU).forward(req, resp);
    }
}
