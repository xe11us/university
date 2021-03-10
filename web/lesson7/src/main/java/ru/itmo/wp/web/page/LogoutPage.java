package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class LogoutPage extends Page {

    @Override
    public void action(HttpServletRequest request, Map<String, Object> view) {
        removeUser(request);

        setMessage(request, "Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}
