package com.orizon.webdriver.domain.model.file.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileAddress {
    private String fileLocation;
    private String fileUrl;

    @Override
    public String toString() {
        return "FileAddress{" +
                "fileLocation='" + fileLocation + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
