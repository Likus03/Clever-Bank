package ru.clevertec.task.controllers.user;

import ru.clevertec.task.repositories.user.UserRepository;
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

@WebServlet(urlPatterns = "/user/registration")
public class UserRegistrationController extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/user/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String phoneNumber = req.getParameter("phoneNumber");
        String firstname = req.getParameter("firstname");
        String surname = req.getParameter("surname");

        try {
            UUID userId = userService.createUser(login, password, phoneNumber, firstname, surname);

            if (userId != null) {
                HttpSession session = req.getSession();
                session.setAttribute("userId", userId);

                req.getRequestDispatcher("/WEB-INF/views/createAccount.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/WEB-INF/views/errors/registration-failed.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
