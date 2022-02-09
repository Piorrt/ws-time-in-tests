package com.example.clock.api;

import com.example.clock.api.model.ArticleDto;
import com.example.clock.api.model.ArticlesResponse;
import com.example.clock.api.model.SaveArticleRequest;
import com.example.clock.domain.model.Article;
import com.example.clock.domain.ArticleService;
import com.example.clock.domain.model.SaveArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class Endpoint {

    private final ArticleService articleService;

    @GetMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArticlesResponse> getAllArticle() {
        List<ArticleDto> articles = articleService.findAllArticles().stream()
            .map(article -> ArticleDto.from(article))
            .collect(Collectors.toList());

        ArticlesResponse response = ArticlesResponse.builder()
            .articles(articles)
            .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(
        path = "/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArticleDto> getArticle(
        @PathVariable(name = "id") Long id
    ) {
        Optional<Article> found = articleService.findById(id);
        if (found.isPresent()) {
            Article article = found.get();
            return ResponseEntity.ok(ArticleDto.from(article));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArticleDto> saveArticle(@RequestBody SaveArticleRequest dto) {
        SaveArticle toSave = new SaveArticle(
            dto.getTitle(),
            dto.getText(),
            dto.getPublicationDate(),
            dto.getEndOfVisibility()
        );
        Article saved = articleService.save(toSave);

        return ResponseEntity.ok(ArticleDto.from(saved));
    }
}