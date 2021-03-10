package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public abstract class Page {
    private final EventService eventService = new EventService();
    private final UserService userService = new UserService();
    private HttpServletRequest request;

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        putUser(view);
        putMessage(view);
        view.put("userCount", userService.findCount());
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void putUser(Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }

    private void putMessage(Map<String, Object> view) {
        String message = getMessage();
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            removeMessage();
        }
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    protected void removeUser() {
        request.getSession().removeAttribute("user");
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    protected String getMessage() {
        return (String) request.getSession().getAttribute("message");
    }

    protected void removeMessage() {
        request.getSession().removeAttribute("message");
    }

    protected UserService getUserService() {
        return userService;
    }

    public EventService getEventService() {
        return eventService;
    }
}
