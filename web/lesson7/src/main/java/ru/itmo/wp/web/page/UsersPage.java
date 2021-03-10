package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage extends Page {
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", getUserService().findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                getUserService().find(Long.parseLong(request.getParameter("userId"))));
    }
}