package com.orizon.webdriver.domain.service.operation;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class FileUpdater {

    private final Scanner scan;

    @Autowired
    public FileUpdater(Scanner scan){
        this.scan = scan;
    }

    public void update(AFileInterface file){
        ((AbstractFile)file).setFileName(scan.nextLine());
    }
}
