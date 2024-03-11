package ru.clevertec.task.controllers.user;

import ru.clevertec.task.services.user.UserService;
import ru.clevertec.task.services.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(urlPatterns = "/user/authorization")
public class UserAuthorizationController extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        validateUser(req, resp, login, password);
    }

    private void validateUser(HttpServletRequest req, HttpServletResponse resp, String login, String password) throws ServletException, IOException {
        UUID userId = userService.getUser(login, password);

        if (userId != null) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", userId);

            goToCreateAccountPage(req, resp);
        } else {
            goToErrorPage(req, resp);
        }

    }

    private static void goToErrorPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/errors/login-failed.jsp").forward(req, resp);
    }

    private static void goToCreateAccountPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/other/menu.jsp").forward(req, resp);
    }
}
