package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.file.fenum.FileType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final List<AFileInterface> files;
    private final FileCreator fileCreater;
    private final FileDeleter fileDeleter;
    private final FileUpdater fileUpdater;
    private final List<AbstractFile.Permission> initialPermission;

    @Autowired
    public FileService(FileCreator fileCreater, FileDeleter fileDeleter, FileUpdater fileUpdater, List<AbstractFile.Permission> initialPermission){
        this.files = new ArrayList<>();
        this.fileCreater = fileCreater;
        this.fileDeleter = fileDeleter;
        this.fileUpdater = fileUpdater;
        this.initialPermission = initialPermission;
    }

    public void deleteFile(AFileInterface file){
        fileDeleter.deleteFile(file);
    }

    public AFileInterface createFile(String type){
        AFileInterface file = fileCreater.create(FileType.from(type.toUpperCase()), Duration.ofMinutes(2));
        ((AbstractFile) file).setFilePermissions(initialPermission);
        processFiles();
        return file;
    }

    public void updateFile(AFileInterface file){
        fileUpdater.update(file);
    }

    private void processFiles(){     // Carregar arquivos no banco
        files.forEach(AFileInterface::save);
        files.stream().map(AFileInterface::load).forEach(files::add);
    }

}
