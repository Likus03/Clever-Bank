package ru.clevertec.task.mappers;

import ru.clevertec.task.entities.User;

import javax.servlet.http.HttpServletRequest;

import static ru.clevertec.task.utils.Constants.*;

public class UserMapper {
    private UserMapper() {
    }

    private static class Holder {
        private static final UserMapper INSTANCE = new UserMapper();
    }

    public static UserMapper getInstance() {
        return Holder.INSTANCE;
    }

    public User buildUser(HttpServletRequest req) {
        return User.builder()
                .login(req.getParameter(LOGIN))
                .password(req.getParameter(PASSWORD))
                .phoneNumber(req.getParameter(PHONENUMBER))
                .firstname(req.getParameter(FIRSTNAME))
                .surname(req.getParameter(SURNAME))
                .build();
    }
}
