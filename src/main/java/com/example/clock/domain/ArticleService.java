package com.example.clock.domain;

import com.example.clock.domain.model.Article;
import com.example.clock.domain.model.SaveArticle;
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

    //TODO - 2. dodaj wcześniej zdefiniowany bean clock do serwisu
    //TODO - 3. użyj clock przy zapisie i odczycie wszystkich artykułów

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
            .createAt(Instant.now())
            .build();

        return repository.save(toSave);
    }

    private boolean isArticleVisible(Article article) {
        Instant currentTime = Instant.now();
        return article.getExpireDate()
            .isAfter(currentTime) &&
            article.getPublicationDate().isBefore(currentTime);
    }
}