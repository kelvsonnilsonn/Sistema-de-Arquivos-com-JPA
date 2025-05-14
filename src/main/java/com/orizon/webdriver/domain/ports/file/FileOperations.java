package com.orizon.webdriver.domain.ports.file;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.infrastructure.repository.FileRepository;

public interface FileOperations {

    FileOperations load();

    void save(FileRepository fileRepository);

    void delete(FileRepository fileRepository);

    void comment(Comment comment);
}
