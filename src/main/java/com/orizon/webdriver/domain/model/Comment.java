package com.orizon.webdriver.domain.model;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public final class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "body")
    private String body;

    @Column(name = "time")
    private Instant time;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AbstractUser author;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private AbstractFile file;

    public Comment(String body) {
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
