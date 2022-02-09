package com.example.clock.domain;

import com.example.clock.domain.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Optional<Article> getArticle(Long articleId);

    List<Article> getAllArticle();
}