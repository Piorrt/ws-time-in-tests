package com.example.clock.api.model;

import com.example.clock.domain.model.Article;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class ArticleDto {

    private Long id;
    private String title;
    private String text;
    private Instant publicationDate;
    private Instant expireDate;
    private Instant createAt;

    public static ArticleDto from(Article article) {
        return ArticleDto.builder()
            .id(article.getId())
            .title(article.getTitle())
            .text(article.getText())
            .publicationDate(article.getPublicationDate())
            .expireDate(article.getExpireDate())
            .createAt(article.getCreateAt())
            .build();
    }

}