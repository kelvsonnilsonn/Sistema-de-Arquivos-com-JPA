package com.orizon.webdriver.domain.model;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
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

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "versions_historical")
public class VersioningHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private Instant creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editor_id")
    private AbstractUser editor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private AbstractFile file;

    @Column(name = "commit", length = 1000, nullable = false)
    private String commitMessage;

    public VersioningHistory(AbstractUser user, AbstractFile file, String commit){
        this.editor = Objects.requireNonNull(user, () -> {throw new ENFieldException();});
        this.file = Objects.requireNonNull(file, () -> {throw new ENFieldException();});
        if(commit.isBlank()){
            throw new ENFieldException();
        }
        this.commitMessage = commit;
        this.creationDate = Instant.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        return String.format(
                """
                🔄 Versão #%d
                📅 Data: %s
                👤 Editor: %s
                📄 Arquivo: %s
                💬 Commit: %s
                """,
                id,
                dateFormatter.format(creationDate),
                editor != null ? editor.getUserLogin() : "N/A",
                file != null ? file.getFileName() : "N/A",
                commitMessage.length() > 100 ?
                        commitMessage.substring(0, 100) + "..." : commitMessage
        );
    }

}
