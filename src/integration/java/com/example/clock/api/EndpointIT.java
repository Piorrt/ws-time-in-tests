package com.example.clock.api;

import com.example.clock.BaseIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EndpointIT extends BaseIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void should_return_article() {
        //given
        Integer articleId = 1;

        //when
        ResponseEntity<ArticleDto> response = callGetArticle(articleId);

        //then
        ArticleDto body = response.getBody();
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(body.getId(), articleId.longValue());
    }

    private ResponseEntity<ArticleDto> callGetArticle(int articleId) {
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

}