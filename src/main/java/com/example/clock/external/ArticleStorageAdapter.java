package com.example.clock.external;

import com.example.clock.domain.model.Article;
import com.example.clock.domain.ArticleRepository;
import com.example.clock.external.storage.ArticleEntity;
import com.example.clock.external.storage.JpaArticleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ArticleStorageAdapter implements ArticleRepository {

    private final JpaArticleRepository repository;

    @Override
    public Article save(Article article) {
        return repository.save(ArticleEntity.from(article))
            .toDomain();
    }

    @Override
    public Optional<Article> getArticle(Long articleId) {
        Optional<ArticleEntity> found = repository.findById(articleId);
        if(found.isPresent()) {
            return Optional.of(found.get().toDomain());
        }
        return Optional.empty();
    }

    @Override
    public List<Article> getAllArticle() {
        return repository.findAll().stream()
            .map(ArticleEntity::toDomain)
            .collect(Collectors.toList());
    }
}