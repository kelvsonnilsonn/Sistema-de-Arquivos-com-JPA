package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.file.fenum.FileType;

import com.orizon.webdriver.domain.repository.FileRepository;
import com.orizon.webdriver.domain.service.operation.FileCreator;
import com.orizon.webdriver.domain.service.operation.FileDeleter;
import com.orizon.webdriver.domain.service.operation.FileUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final List<AFileInterface> files;
    private final FileCreator fileCreator;
    private final FileDeleter fileDeleter;
    private final FileUpdater fileUpdater;
    private final FileRepository fileRepository;
    private final List<AbstractFile.Permission> initialPermission;

    @Autowired
    public FileService(FileCreator fileCreator, FileDeleter fileDeleter, FileUpdater fileUpdater, FileRepository fileRepository, List<AbstractFile.Permission> initialPermission){
        this.files = new ArrayList<>();
        this.fileCreator = fileCreator;
        this.fileDeleter = fileDeleter;
        this.fileUpdater = fileUpdater;
        this.fileRepository = fileRepository;
        this.initialPermission = initialPermission;
    }

    public void delete(AFileInterface file){
        fileDeleter.delete(file, fileRepository);
    }

    public AFileInterface create(String type){
        AFileInterface file = fileCreator.create(FileType.from(type.toUpperCase()), Duration.ofMinutes(2));
        ((AbstractFile) file).setFilePermissions(initialPermission);
        return file;
    }

    public void addComment(AFileInterface file, String comment){
        file.comment(new Comment(comment));
    }

    public void update(AFileInterface file){
        fileUpdater.update(file);
    }

    public void search(int id){
        System.out.println(files.stream().filter(f -> ((AbstractFile) f).getId() == id));
    }

    public void search(String fileName){
        System.out.println(files.stream().filter(f -> ((AbstractFile) f).getFileName().equals(fileName)).collect(Collectors.toList()));
    }

    public void search(AFileInterface file){
        System.out.println(files.contains(file));
    }

    public void processFiles(){     // Carregar arquivos/atualizações no banco
        files.forEach(file -> file.save(fileRepository));
        files.stream().map(AFileInterface::load).forEach(files::add);
    }
}
