package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;

public interface UserService {
    void listAll();
    AbstractUser findOne(Long id);
    void save(AbstractUser user);
    void delete(Long id);
    void update(AbstractUser user);
    void comment(AbstractUser user, AbstractFile file, Comment comment);
}
