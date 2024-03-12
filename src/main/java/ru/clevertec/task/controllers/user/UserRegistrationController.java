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
import java.util.UUID;

import static ru.clevertec.task.utils.Constants.*;

@WebServlet(REGISTRATION_URL)
public class UserRegistrationController extends HttpServlet {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String phoneNumber = req.getParameter(PHONENUMBER);
        String firstname = req.getParameter(FIRSTNAME);
        String surname = req.getParameter(SURNAME);

        UUID userId = userService.createUser(login, password, phoneNumber, firstname, surname);

        if (userId != null) {
            HttpSession session = req.getSession();
            session.setAttribute(USER_ID, userId);

            req.getRequestDispatcher(ACCOUNT_CREATE_PAGE).forward(req, resp);
        } else {
            req.getRequestDispatcher(REGISTRATION_FAILED_PAGE).forward(req, resp);
        }

    }
}
