package com.example.clock.external;

import com.example.clock.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "article")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String text;
    private Instant publicationDate;
    private Instant expireDate;
    private Instant createAt;

    public static ArticleEntity from(Article article) {
        return ArticleEntity.builder()
            .id(article.getId())
            .title(article.getTitle())
            .text(article.getText())
            .publicationDate(article.getPublicationDate())
            .expireDate(article.getExpireDate())
            .createAt(article.getCreateAt())
            .build();
    }

    public Article toDomain() {
        return Article.builder()
            .id(id)
            .title(title)
            .text(text)
            .publicationDate(publicationDate)
            .expireDate(expireDate)
            .createAt(createAt)
            .build();
    }
}