package com.orizon.webdriver.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

@Getter
public final class Comment {
    private final Integer id;
    private final String body;
    private final Instant time;

    public Comment(Integer id, String body, Instant time){
        this.id = id;
        this.body = Objects.requireNonNull(body, "O comentário não pode ser nulo.");
        this.time = time;
    }
}
