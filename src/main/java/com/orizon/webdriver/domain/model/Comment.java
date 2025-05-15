package com.orizon.webdriver.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Setter
@Getter
public final class Comment {
    private long id;
    private final String body;
    private final Instant time;

    public Comment(String body){
        this.body = Objects.requireNonNull(body, "O comentário não pode ser nulo.");
        this.time = Instant.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        String formattedTime = formatter.format(time);

        return String.format(
                """
                ➤ Comentário #%d
                  Data: %s
                  Texto: "%s"
                """,
                id,
                formattedTime,
                body
        );
    }
}
