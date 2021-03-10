package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void save(Article article) {
        articleRepository.save(article);
    }

    public void validateArticle(Article article, User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException("You must be logged in");
        }

        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("Title must be not empty");
        }
        if (article.getTitle().length() > 255) {
            throw new ValidationException("Title must be less than 256 symbols");
        }

        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("Text must be not empty");
        }
        if (article.getText().length() > 255) {
            throw new ValidationException("Text must be less than 256 symbols");
        }
    }

    public void validateHiddenChange(User loggedUser, String stringId, String stringHidden) throws ValidationException {
        if (loggedUser == null) {
            throw new ValidationException("You must login");
        }

        if (!isLong(stringId)) {
            throw new ValidationException("Id must be a number");
        }

        Article article = find(Long.parseLong(stringId));
        if (article.getUserId() != loggedUser.getId()) {
            throw new ValidationException("It's not your post!");
        }

        if (!isBoolean(stringHidden)) {
            throw new ValidationException("Hidden must be a boolean");
        }
    }

    private boolean isBoolean(String string) {
        return "true".equalsIgnoreCase(string) || "false".equalsIgnoreCase(string);
    }

    private boolean isLong(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public void updateHidden(long id, boolean hidden) {
        articleRepository.updateHidden(id, hidden);
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAllByUserId(long userId) {
        return articleRepository.findAllByUserId(userId);
    }

    public List<Article> findAllNotHidden() {
        return articleRepository.findAllNotHidden();
    }
}
