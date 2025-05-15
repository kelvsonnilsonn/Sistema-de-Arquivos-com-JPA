package com.orizon.webdriver.domain.ports.file;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.infrastructure.repository.FileRepositoryImpl;

public interface FileOperations {

    FileOperations load();

    void save(FileRepositoryImpl fileRepository);

    void delete(FileRepositoryImpl fileRepository);

    void comment(Comment comment);
}
