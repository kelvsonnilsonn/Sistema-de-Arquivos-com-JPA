package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.FileOperation;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;

public interface FileService {
    void listAll();
    AbstractFile findOne(Long id);
    void create(AbstractUser user, String filename, AbstractFile.FileType type);
    void delete(Long id);
    void update(Long id, String name, FileOperation.OperationType type);
}
