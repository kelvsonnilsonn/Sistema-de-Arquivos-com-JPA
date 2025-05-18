package com.orizon.webdriver.domain.model;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.Administrator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "support_requests")
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private AbstractUser author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_id")
    private Administrator admin;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body", nullable = false) // length = 255
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private AbstractFile file;

    @Enumerated(EnumType.STRING)
    private SupportStatus status;

    @Column(name = "creation_date")
    private Instant creation;

    public enum SupportStatus {
        PENDING, RESOLVED, REJECTED
    }

    private void setStatus(SupportStatus status){
        if(this.status == SupportStatus.RESOLVED && status != SupportStatus.RESOLVED){
            throw new IllegalArgumentException("Proibido mudar o status de um suporte já fechado.");
        }
        this.status = status;
    }

    public void reject(){ setStatus(SupportStatus.REJECTED); }
    public void resolve() { setStatus(SupportStatus.RESOLVED); }

    public boolean isResolved() {
        return this.status == SupportStatus.RESOLVED;
    }

    public Support(AbstractUser user, AbstractFile file, String title, String body){
        this.author = Objects.requireNonNull(user, () -> { throw new ENFieldException();});
        this.file = Objects.requireNonNull(file, () -> { throw new ENFieldException();});
        this.title = Objects.requireNonNull(title, () -> {throw new ENFieldException();});
        this.body = Objects.requireNonNull(body, () -> {throw new ENFieldException();});
        this.creation = Instant.now();
        this.status = SupportStatus.PENDING;
    }

    @Override
    public String toString() {
        return String.format(
                """
                🆔 ID do Suporte: %d
                ✍️ Autor: %s (ID: %d)
                📝 Mensagem:
                   "%s"
                🔄 Status: %s
                """,
                id,
                author.getUserLogin(), author.getId(),
                body,
                status == SupportStatus.RESOLVED ? "✅ Resolvido" : "🟡 Pendente"
        );
    }
}
