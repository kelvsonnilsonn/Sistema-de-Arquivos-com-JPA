package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;

public interface CommentService {
    void listAll();
    Comment findOne(Long id);
    void create(String body, AbstractUser user, AbstractFile file);
    void delete(Long id);
    void update(Long id, String body);
}
