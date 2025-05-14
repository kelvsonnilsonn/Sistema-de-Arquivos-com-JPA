package com.orizon.webdriver.domain.service.operation;

import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.file.fenum.FileType;
import com.orizon.webdriver.domain.model.file.GenericFile;
import com.orizon.webdriver.domain.model.file.VideoFile;
import com.orizon.webdriver.domain.model.file.data.FileInformations;
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

    public AFileInterface create(FileType type, Duration duration){
        return switch (type){
            case TEXT, PHOTO -> new GenericFile(new FileInformations(getFileName(type.getDescription())));
            case VIDEO -> new VideoFile(new FileInformations(getFileName(type.getDescription())), duration);
        };
    }

    private String getFileName(String type){
        System.out.println("Digite o nome do arquivo de " + type);
        return scan.nextLine();
    }
}
