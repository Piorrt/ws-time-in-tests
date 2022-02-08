package com.example.clock.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class SaveArticle {

    private final String title;
    private final String text;
    private final Instant publicationDate;
    private final Instant endOfVisibility;

}