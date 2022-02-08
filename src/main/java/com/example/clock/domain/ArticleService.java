package com.example.clock.domain;

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

    public List<Article> findAllArticles() {
        return repository.getAllArticle().stream()
            .filter(article -> article.isVisible())
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
            .createAt(Instant.now())
            .build();

        return repository.save(toSave);
    }
}