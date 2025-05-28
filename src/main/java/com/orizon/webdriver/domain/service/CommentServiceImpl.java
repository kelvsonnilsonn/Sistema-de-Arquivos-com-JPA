package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.CommentInexistentException;
import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.infra.persistence.repositories.CommentRepository;
import com.orizon.webdriver.domain.ports.service.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentDAO;

    public CommentServiceImpl(CommentRepository commentDAO){
        this.commentDAO = commentDAO;
    }

    @Override
    public void create(Comment comment) {
        commentDAO.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentDAO.findById(id).orElseThrow(CommentInexistentException::new);
    }

    @Override
    public void findAll() {
        commentDAO.findAll().forEach(System.out::println);
    }



    @Override
    public void delete(Long id) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Comment comment = findById(id);
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
        toEdit.setCreatedAt(Instant.now());
        commentDAO.save(toEdit);
    }

    @Override
    public Set<Comment> findByFileId(Long fileId){
        return commentDAO.findByFileId(fileId);
    }

    @Override
    public Set<Comment> findByAuthorId(Long authorId){
        return commentDAO.findByAuthorId(authorId);
    }
}
