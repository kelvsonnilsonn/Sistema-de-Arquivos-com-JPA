package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.CommentInexistentException;
import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.ports.repository.CommentRepository;
import com.orizon.webdriver.domain.ports.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentDAO;

    public CommentServiceImpl(CommentRepository commentDAO){
        this.commentDAO = commentDAO;
    }

    @Override
    public void listAll() {
        commentDAO.findAll().forEach(System.out::println);
    }

    @Override
    public Comment findOne(Long id) {
        return commentDAO.findById(id).orElseThrow(CommentInexistentException::new);
    }

    @Override
    public void save(Comment comment) {
        commentDAO.save(comment);
    }

    @Override
    public void delete(Long id) {
        commentDAO.deleteById(id);
    }

    @Override
    public void update(Comment comment) {
        commentDAO.save(comment);
    }
}
