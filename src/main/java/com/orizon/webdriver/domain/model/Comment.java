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
import java.util.Objects;

@Setter
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public final class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        String formattedTime = time != null ? formatter.format(time) : "Data indefinida";

        String authorInfo = (author != null)
                ? String.format("%s (ID: %d)", author.getUserLogin(), author.getId())
                : "Autor desconhecido";

        String fileInfo = (file != null)
                ? String.format("%s (ID: %d)", file.getFileName(), file.getId())
                : "Arquivo não vinculado";

        return String.format(
                """
                💬 Comentário #%d
                📅 Data: %s
                📝 Texto: "%s"
                🧑 Autor: %s
                📂 Arquivo: %s
                """,
                id,
                formattedTime,
                body,
                authorInfo,
                fileInfo
        );
    }
}
