package com.orizon.webdriver.domain.model.file.data;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class FileInformations {
    private FileData fileData;
    private String fileName;

    public FileInformations(String fileName){
        this.fileName = fileName;
    }

    public void setFileSize(int size){this.fileData.setFileSize(size);}
    public void setFileReleaseDate(Date date) {this.fileData.setFileReleaseDate(date);}

    @Override
    public String toString() {
        return "FileInformations{" +
                "fileData=" + fileData +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
