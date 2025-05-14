package com.orizon.webdriver.domain.model.file.data;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class FileData {
    private FileAddress fileAddress;
    private int fileSize;
    private Date fileReleaseDate;

    public void setFileLocation(String location) {this.fileAddress.setFileLocation(location);}
    public void setFileURL(String URL) {this.fileAddress.setFileLocation(URL);}

    @Override
    public String toString() {
        return "FileData{" +
                "fileAddress=" + fileAddress +
                ", fileSize=" + fileSize +
                ", fileReleaseDate=" + fileReleaseDate +
                '}';
    }
}
