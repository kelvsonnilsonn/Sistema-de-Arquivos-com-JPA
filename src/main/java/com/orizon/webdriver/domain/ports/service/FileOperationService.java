package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.FileOperation;

import java.util.List;

public interface FileOperationService {
    void listAll();
    List<FileOperation> findByFileId(Long id);
    List<FileOperation> findByUserId(Long id);
    void save(FileOperation operation);
}
