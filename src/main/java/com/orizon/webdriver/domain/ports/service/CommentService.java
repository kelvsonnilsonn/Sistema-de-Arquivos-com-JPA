package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Comment;

public interface CommentService {
    void listAll();
    Comment findOne(Long id);
    void save(Comment comment);
    void delete(Long id);
    void update(Comment comment);
}
