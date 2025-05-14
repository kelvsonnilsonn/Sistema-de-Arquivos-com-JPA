package com.orizon.webdriver.domain.service.operation;

import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.enums.FileType;
import com.orizon.webdriver.domain.model.file.GenericFile;
import com.orizon.webdriver.domain.model.file.VideoFile;
import com.orizon.webdriver.domain.model.file.FileMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Scanner;

@Component
public class FileCreator {

    private final Scanner scan;

    @Autowired
    public FileCreator(Scanner scan){
        this.scan = scan;
    }

    public FileOperations create(FileType type, Duration duration){
        return switch (type){
            case TEXT, PHOTO -> new GenericFile(new FileMetaData(getFileName(type.getDescription())));
            case VIDEO -> new VideoFile(new FileMetaData(getFileName(type.getDescription())), duration);
        };
    }

    private String getFileName(String type){
        System.out.println("Digite o nome do arquivo de " + type);
        return scan.nextLine();
    }
}
