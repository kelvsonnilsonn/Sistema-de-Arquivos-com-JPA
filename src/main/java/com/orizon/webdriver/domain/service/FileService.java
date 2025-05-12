package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.file.AFileInterface;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.GenericFile;
import com.orizon.webdriver.domain.model.file.VideoFile;
import com.orizon.webdriver.domain.model.file.filedatas.FileInformations;
import com.orizon.webdriver.exceptions.InvalidFileException;
import com.orizon.webdriver.exceptions.InvalidFileTypeException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
public class FileService {

    private final List<AFileInterface> files;
    private final Scanner scan;

    public FileService(Scanner scan){
        this.files = new ArrayList<>();
        this.scan = scan;
    }

    public void processFiles(){     // Carregar arquivos no banco
        files.forEach(AFileInterface::save);
        files.stream().map(AFileInterface::load).forEach(files::add);
    }

    public void deleteFile(AFileInterface file){
        Objects.requireNonNull(file, "Arquivo a remover não pode ser nulo.");
        if(!(file instanceof AbstractFile)){
            throw new InvalidFileException();
        }

        files.remove(file);
        file.deleteFile();
    }

    public void createFile(String type){
        AFileInterface file = checkType(type);
        files.add(file);
    }

    private AFileInterface checkType(String type){
        switch (type.toUpperCase()){
            case "VIDEO" -> {
                return new VideoFile(new FileInformations(getFileName("VIDEO")), Duration.ofMinutes(2));
            }
            case "TEXT" -> {
                return new GenericFile(new FileInformations(getFileName("TEXT")));
            }
            default -> {throw new InvalidFileTypeException(); }
        }
    }

    private String getFileName(String type){
        System.out.println("Digite o nome do arquivo de " + type);
        return scan.nextLine();
    }
}
