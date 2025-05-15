package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.ports.file.FileOperations;

import java.util.List;

public interface FileService {
    void delete(FileOperations file);
    FileOperations create(String type);
    void addComment(FileOperations file, String comment);
    void update(FileOperations file);
    void search(int id);
    List<FileOperations> search(String fileName);
    void search(FileOperations file);
}
