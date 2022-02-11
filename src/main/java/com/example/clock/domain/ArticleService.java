package com.example.clock.domain;

import com.example.clock.domain.model.Article;
import com.example.clock.domain.model.SaveArticle;
import com.example.clock.domain.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository repository;
    private final TimeProvider timeProvider;

    public List<Article> findAllArticles() {
        return repository.getAllArticle().stream()
            .filter(article -> isArticleVisible(article))
            .collect(Collectors.toList());
    }

    public Optional<Article> findById(Long id) {
        return repository.getArticle(id);
    }

    public Article save(SaveArticle request) {
        Article toSave = Article.builder()
            .title(request.getTitle())
            .text(request.getText())
            .publicationDate(request.getPublicationDate())
            .expireDate(request.getEndOfVisibility())
            .createAt(timeProvider.getInstantNow())
            .build();

        return repository.save(toSave);
    }

    private boolean isArticleVisible(Article article) {
        Instant currentTime = timeProvider.getInstantNow();
        return article.getExpireDate()
            .isAfter(currentTime) &&
            article.getPublicationDate().isBefore(currentTime);
    }
}