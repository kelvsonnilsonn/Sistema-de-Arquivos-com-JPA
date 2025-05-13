package com.orizon.webdriver.domain.model.file.filedatas;

import lombok.AllArgsConstructor;
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
