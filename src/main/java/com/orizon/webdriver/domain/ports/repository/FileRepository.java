package com.orizon.webdriver.domain.ports.repository;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.ports.file.FileOperations;

import java.util.List;
import java.util.Optional;

public interface FileRepository {
    FileOperations create(String type, List<AbstractFile.Permission> initialPerms);
    void delete(FileOperations file);
    void update(FileOperations file);
    Optional<FileOperations> search(int id);
    List<FileOperations> search(String fileName);
    boolean search(FileOperations file);
}
