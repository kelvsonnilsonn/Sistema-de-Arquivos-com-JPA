package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.CommentInexistentException;
import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.infra.repositories.CommentRepository;
import com.orizon.webdriver.domain.ports.service.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
@Transactional
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
    public void create(String body, AbstractUser user, AbstractFile file) {
        Comment comment = new Comment(body);
        if(user.addComment(comment) && file.addComment(comment)){
            commentDAO.save(comment);
        }
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Comment comment = findOne(id);
        AbstractUser user = comment.getAuthor();
        AbstractFile file = comment.getFile();
        if(user.removeComment(comment) && file.removeComment(comment)){
            commentDAO.deleteById(id);
        }
    }

    @Override
    public void update(Long id, String body) {
        Objects.requireNonNull(body, () -> {throw new ENFieldException();});
        Comment toEdit = commentDAO.findById(id).orElseThrow(CommentInexistentException::new);
        toEdit.setBody(body);
        toEdit.setTime(Instant.now());
        commentDAO.save(toEdit);
    }
}
