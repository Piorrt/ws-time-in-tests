package com.example.clock.config;

import com.example.clock.domain.ArticleRepository;
import com.example.clock.domain.ArticleService;
import com.example.clock.external.ArticleStorageAdapter;
import com.example.clock.external.storage.JpaArticleRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public ArticleRepository articleRepository(JpaArticleRepository repository) {
        return new ArticleStorageAdapter(repository);
    }

    @Bean
    public ArticleService articleService(
        ArticleRepository repository
    ) {
        return new ArticleService(repository);
    }
}