package com.example.clock.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class Article {

    private Long id;
    private String title;
    private String text;
    private Instant publicationDate;
    private Instant expireDate;
    private Instant createAt;

}