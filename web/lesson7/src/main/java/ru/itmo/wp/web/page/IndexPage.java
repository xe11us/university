package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @noinspection unused
 */
public class IndexPage extends Page {
    @Override
    public void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
    }

    @Json
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        List<Article> articles = getArticleService().findAllNotHidden();
        view.put("articles", articles);

        List<Long> ids = new ArrayList<>();

        for (Article article : articles) {
            ids.add(article.getUserId());
        }

        view.put("usersMap", getUserService().findUsersMapByIds(ids));
    }
}