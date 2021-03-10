package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @noinspection unused
 */
public class MyArticlesPage extends Page {
    @Override
    public void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser(request) == null) {
            setMessage(request, "You must register before visiting this page");
            throw new RedirectException("/index");
        }

        List<Article> articles = getArticleService().findAllByUserId(getUser(request).getId());
        view.put("articles", articles);
    }

    public void changeHiddenState(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String stringId = request.getParameter("id");
        String stringHidden = request.getParameter("hidden");

        getArticleService().validateHiddenChange((User) request.getSession().getAttribute("user"),
                stringId, stringHidden);

        getArticleService().updateHidden(Long.parseLong(stringId), Boolean.parseBoolean(stringHidden));
    }
}