package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.FileOperation;

import java.util.List;
import java.util.Set;

public interface FileOperationService {
    void findAll();
    Set<FileOperation> findByFileId(Long id);
    Set<FileOperation> findByUserId(Long id);
    FileOperation findById(Long id);
    void create(FileOperation operation);
    void update(FileOperation operation);
}
