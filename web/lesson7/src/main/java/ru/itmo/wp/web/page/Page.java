package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public abstract class Page {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    public ArticleService getArticleService() {
        return articleService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    protected void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = getMessage(request);
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    protected void setMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("message", message);
    }

    protected String getMessage(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("message");
    }

    protected void removeMessage(HttpServletRequest request) {
        request.getSession().removeAttribute("message");
    }

    protected void setUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    protected void removeUser(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
    }
}
