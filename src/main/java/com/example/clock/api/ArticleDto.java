package com.example.clock.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ArticleDto {

    private Long id;
    private String title;
    private String text;
    private LocalDate publicationDate;
    private LocalDateTime createAt;

}