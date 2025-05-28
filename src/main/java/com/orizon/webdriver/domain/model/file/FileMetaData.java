package com.orizon.webdriver.domain.model.file;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class FileMetaData { private int fileSize;
    private Instant fileReleaseDate;
    private String fileLocation;
    private String fileUrl;
}
