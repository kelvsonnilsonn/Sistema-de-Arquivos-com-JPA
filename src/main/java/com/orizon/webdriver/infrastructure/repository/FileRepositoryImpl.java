package com.orizon.webdriver.infrastructure.repository;

import com.orizon.webdriver.domain.enums.FileType;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.ports.repository.FileRepository;
import com.orizon.webdriver.domain.service.operation.FileCreator;
import com.orizon.webdriver.domain.service.operation.FileDeleter;
import com.orizon.webdriver.domain.service.operation.FileUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FileRepositoryImpl implements FileRepository {

    private final FileCreator fileCreator;
    private final FileDeleter fileDeleter;
    private final FileUpdater fileUpdater;
    private final List<FileOperations> files;

    @Autowired
    public FileRepositoryImpl(FileCreator fileCreator, FileDeleter fileDeleter, FileUpdater fileUpdater) {
        this.files = new ArrayList<>();
        this.fileCreator = fileCreator;
        this.fileDeleter = fileDeleter;
        this.fileUpdater = fileUpdater;
    }

    public List<FileOperations> getAllFiles(){
        return new ArrayList<>(files);
    }

    @Override
    public FileOperations create(String type, List<AbstractFile.Permission> initialPerms) {
        FileOperations file = fileCreator.create(FileType.from(type.toUpperCase()), Duration.ofMinutes(2));
        ((AbstractFile) file).setFilePermissions(initialPerms);
        files.add(file);
        return file;
    }

    @Override
    public void delete(FileOperations file) {
        files.remove(file);
        fileDeleter.delete(file, this);
    }

    @Override
    public void update(FileOperations file) {
        fileUpdater.update(file);
    }

    @Override
    public Optional<FileOperations> search(int id) {
        return files.stream()
                .filter(f -> ((AbstractFile) f).getId() == id)
                .findFirst();
    }

    @Override
    public List<FileOperations> search(String fileName) {
        return files.stream()
                .filter(f -> ((AbstractFile) f).getFileName().equals(fileName))
                .collect(Collectors.toList());
    }

    @Override
    public boolean search(FileOperations file) {
        return files.contains(file);
    }
}
