package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;

import java.util.Set;

public interface CommentService {
    void findAll();
    Comment findById(Long id);
    void create(String body, AbstractUser user, AbstractFile file);
    void delete(Long id);
    void update(Long id, String body);
    Set<Comment> findByFileId(Long fileId);
    Set<Comment> findByAuthorId(Long userId);
}
