package com.example.clock.api;

import com.example.clock.BaseIT;
import com.example.clock.api.model.ArticleDto;
import com.example.clock.api.model.ArticlesResponse;
import com.example.clock.api.model.SaveArticleRequest;
import com.example.clock.domain.model.Article;
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
        Article savedArticle = Article.builder()
            .id(1L)
            .title("Article 1")
            .text("Article text")
            .publicationDate(Instant.parse("2020-01-02T00:00:00Z"))
            .expireDate(Instant.parse("2020-01-10T00:00:00Z"))
            .createAt(Instant.parse("2019-12-10T00:00:00Z"))
            .build();
        storage.save(savedArticle);

        //when
        ResponseEntity<ArticleDto> response = callGetArticle(savedArticle.getId());

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        ArticleDto body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(savedArticle.getId(), body.getId());
        Assertions.assertEquals(savedArticle.getTitle(), body.getTitle());
        Assertions.assertEquals(savedArticle.getText(), body.getText());
        Assertions.assertEquals(savedArticle.getPublicationDate(), body.getPublicationDate());
        Assertions.assertEquals(savedArticle.getCreateAt(), body.getCreateAt());
    }

    @Test
    public void should_return_articles_response() {
        //given
        Article savedArticle = Article.builder()
            .id(1L)
            .title("Article 1")
            .text("Article text")
            .publicationDate(Instant.parse("2020-01-02T00:00:00Z"))
            .expireDate(Instant.parse("2020-01-10T00:00:00Z"))
            .createAt(Instant.parse("2019-12-10T00:00:00Z"))
            .build();
        storage.save(savedArticle);

        //when article is not publish - should not return article before publish date
        timeProvider.set("2019-12-27T00:00:00Z");
        ResponseEntity<ArticlesResponse> responseBeforePublish = callGetAllArticle();

        //then
        Assertions.assertEquals(HttpStatus.OK, responseBeforePublish.getStatusCode());
        ArticlesResponse bodyBeforePublish = responseBeforePublish.getBody();
        Assertions.assertNotNull(bodyBeforePublish);
        Assertions.assertEquals(0, bodyBeforePublish.getArticles().size());

        //when after published and before expire date
        timeProvider.set("2020-01-08T00:00:00Z");
        ResponseEntity<ArticlesResponse> responseAfterPublish = callGetAllArticle();

        //then
        ArticlesResponse body = responseAfterPublish.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(responseAfterPublish.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(body.getArticles().size(), 1);
        ArticleDto article = body.getArticles().get(0);
        Assertions.assertEquals(savedArticle.getId(), article.getId());
        Assertions.assertEquals(savedArticle.getTitle(), article.getTitle());
        Assertions.assertEquals(savedArticle.getText(), article.getText());
        Assertions.assertEquals(savedArticle.getPublicationDate(), article.getPublicationDate());
        Assertions.assertEquals(savedArticle.getExpireDate(), article.getExpireDate());
        Assertions.assertEquals(savedArticle.getCreateAt(), article.getCreateAt());

        //when article is expired - should not return article when article was expire
        timeProvider.plusDays(15);
        ResponseEntity<ArticlesResponse> responseAfterExpire = callGetAllArticle();

        //then
        ArticlesResponse bodyAfterExpire = responseAfterExpire.getBody();
        Assertions.assertNotNull(bodyAfterExpire);
        Assertions.assertEquals(responseAfterExpire.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(0, bodyAfterExpire.getArticles().size());
    }

    @Test
    public void should_save_article() {
        //given
        timeProvider.set("2019-12-15T00:00:00Z");
        var now = timeProvider.getInstantNow();
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
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(toSave.getTitle(), body.getTitle());
        Assertions.assertEquals(toSave.getText(), body.getText());
        Assertions.assertEquals(toSave.getPublicationDate(), body.getPublicationDate());
        Assertions.assertEquals(toSave.getEndOfVisibility(), body.getExpireDate());
        Assertions.assertEquals(now, body.getCreateAt());
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