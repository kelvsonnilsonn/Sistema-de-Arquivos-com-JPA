package com.orizon.webdriver.domain.model.file;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class FileMetaData {
    private String fileName;
    private int fileSize;
    private Date fileReleaseDate;
    private String fileLocation;
    private String fileUrl;

    public FileMetaData(String fileName){
        this.fileName = fileName;
    }
}
