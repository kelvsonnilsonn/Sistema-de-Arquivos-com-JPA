package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.file.AbstractFile;

public interface FileService {
    void listAll();
    AbstractFile findOne(Long id);
    void save(AbstractFile file);
    void delete(Long id);
    void update(AbstractFile file);
}
