package com.example.clock.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class Endpoint {

    @GetMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ArticleDto>> getAllArticle() {
        List<ArticleDto> articles = new ArrayList<>();
        articles.add(
            new ArticleDto(
                1L, "Article title 1", "Article text and bla ble bli",
                LocalDate.parse("2022-01-02"), LocalDateTime.parse("2022-01-01T10:15:30")
            )
        );
        articles.add(
            new ArticleDto(
                2L, "Article title 2", "Article text and bla ble bli",
                LocalDate.parse("2022-01-10"), LocalDateTime.parse("2022-01-01T10:15:30")
            )
        );
        return ResponseEntity.ok(articles);
    }

    @GetMapping(
        path = "/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArticleDto> getArticle(
        @PathVariable(name = "id") Integer id
    ) {
        List<ArticleDto> articles = new ArrayList<>();
        articles.add(
            new ArticleDto(
                1L, "Article title 1", "Article text and bla ble bli",
                LocalDate.parse("2022-01-02"), LocalDateTime.parse("2022-01-01T10:15:30")
            )
        );
        articles.add(
            new ArticleDto(
                2L, "Article title 2", "Article text and bla ble bli",
                LocalDate.parse("2022-01-10"), LocalDateTime.parse("2022-01-02T10:15:30")
            )
        );

        ArticleDto found = articles.stream()
            .filter(articleDto -> articleDto.getId() == id.longValue())
            .findFirst()
            .orElseGet(null);

        return ResponseEntity.ok(found);
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArticleDto> saveArticle(ArticleDto dto) {
        return ResponseEntity.ok(dto);
    }
}