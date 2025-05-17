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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editor_id")
    private AbstractUser editor;

    @ManyToOne(fetch = FetchType.LAZY)
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

}
