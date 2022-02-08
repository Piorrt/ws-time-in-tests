package com.example.clock.api;

import com.example.clock.BaseIT;
import com.example.clock.domain.Article;
import com.example.clock.external.ArticleStorageAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class EndpointIT extends BaseIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ArticleStorageAdapter storage;

    @Test
    public void should_return_article() {
        //given
        Article article1 = Article.builder()
            .id(1L)
            .title("Article 1")
            .text("Article text")
            .publicationDate(Instant.parse("2020-01-02T00:00:00Z"))
            .expireDate(Instant.parse("2020-01-10T00:00:00Z"))
            .createAt(Instant.now())
            .build();
        storage.save(article1);

        //when
        ResponseEntity<ArticleDto> response = callGetArticle(article1.getId());

        //then
        ArticleDto body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(body.getId(), article1.getId());
        Assertions.assertEquals(body.getTitle(), article1.getTitle());
        Assertions.assertEquals(body.getText(), article1.getText());
        Assertions.assertEquals(body.getPublicationDate(), article1.getPublicationDate());
        Assertions.assertEquals(body.getCreateAt(), article1.getCreateAt());
    }

    @Test
    public void should_return_articles_response() {
        //given
        Article article1 = Article.builder()
            .id(1L)
            .title("Article 1")
            .text("Article text")
            .publicationDate(Instant.parse("2020-01-02T00:00:00Z"))
            .expireDate(Instant.parse("2020-01-10T00:00:00Z"))
            .createAt(Instant.now())
            .build();
        storage.save(article1);

        //when
        ResponseEntity<ArticlesResponse> response = callGetAllArticle();

        //then
        ArticlesResponse body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(body.getArticles().size(), 1);
        ArticleDto article = body.getArticles().get(0);
        Assertions.assertEquals(article.getId(), article1.getId());
        Assertions.assertEquals(article.getTitle(), article1.getTitle());
        Assertions.assertEquals(article.getText(), article1.getText());
        Assertions.assertEquals(article.getPublicationDate(), article1.getPublicationDate());
        Assertions.assertEquals(article.getExpireDate(), article1.getExpireDate());
        Assertions.assertEquals(article.getCreateAt(), article1.getCreateAt());
    }

    @Test
    public void should_save_article() {
        //given
        SaveArticleRequest toSave = SaveArticleRequest.builder()
            .title("Article 1")
            .text("Article text")
            .publicationDate(Instant.parse("2020-01-01T00:00:00Z"))
            .endOfVisibility(Instant.parse("2020-01-10T00:00:00Z"))
            .build();

        //when
        ResponseEntity<ArticleDto> response = callSaveArticle(toSave);

        //then
        ArticleDto body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(body.getTitle(), toSave.getTitle());
        Assertions.assertEquals(body.getText(), toSave.getText());
        Assertions.assertEquals(body.getPublicationDate(), toSave.getPublicationDate());
        Assertions.assertEquals(body.getExpireDate(), toSave.getEndOfVisibility());
    }

    private ResponseEntity<ArticleDto> callGetArticle(Long articleId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        return restTemplate.exchange(
            localUrl("/articles/" + articleId),
            HttpMethod.GET,
            new HttpEntity(headers),
            ArticleDto.class
        );
    }

    private ResponseEntity<ArticlesResponse> callGetAllArticle() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        return restTemplate.exchange(
            localUrl("/articles"),
            HttpMethod.GET,
            new HttpEntity(headers),
            ArticlesResponse.class
        );
    }

    private ResponseEntity<ArticleDto> callSaveArticle(SaveArticleRequest body) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        return restTemplate.exchange(
            localUrl("/articles"),
            HttpMethod.POST,
            new HttpEntity(body, headers),
            ArticleDto.class
        );
    }

}