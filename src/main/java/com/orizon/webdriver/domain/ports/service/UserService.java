package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.ports.file.FileOperations;

public interface UserService {
    FileOperations create(String type, AbstractUser user);
    void delete(FileOperations file, AbstractUser user);
    void edit(FileOperations file);
    void comment(FileOperations file, String comment);
}
