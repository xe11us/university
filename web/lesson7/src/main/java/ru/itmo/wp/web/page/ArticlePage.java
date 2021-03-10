package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class ArticlePage extends Page {
    @Override
    public void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser(request) == null) {
            setMessage(request, "You must register before writing articles");
            throw new RedirectException("/index");
        }
    }

    @Json
    private void post(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Article article = new Article();
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));

        User user = getUser(request);
        getArticleService().validateArticle(article, user);
        article.setUserId(user.getId());

        getArticleService().save(article);
        setMessage(request, "Successfully posted!");
        throw new RedirectException("/index");
    }
}
