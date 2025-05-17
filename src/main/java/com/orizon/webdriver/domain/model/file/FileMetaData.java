package com.orizon.webdriver.domain.model.file;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Embeddable
public class FileMetaData {
    private String fileName;
    private int fileSize;
    private Instant fileReleaseDate;
    private String fileLocation;
    private String fileUrl;

    public FileMetaData(String fileName){
        this.fileName = fileName;
    }
}
