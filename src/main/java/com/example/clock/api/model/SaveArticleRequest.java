package com.example.clock.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Getter
public class SaveArticleRequest {

    private String title;
    private String text;
    private Instant publicationDate;
    private Instant endOfVisibility;

}