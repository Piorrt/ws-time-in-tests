package com.example.clock.domain;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Optional<Article> getArticle(Long articleId);

    List<Article> getAllArticle();
}