package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.UserInexistentException;
import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.infra.repositories.UserRepository;
import com.orizon.webdriver.domain.ports.service.CommentService;
import com.orizon.webdriver.domain.ports.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userDAO;
    private final CommentService commentDAO;

    @Autowired
    public UserServiceImpl(UserRepository userDAO, CommentService commentDAO){
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
    }

    @Override
    public void listAll() {
        userDAO.findAll().forEach(System.out::println);
    }

    @Override
    public AbstractUser findOne(Long id) {
        return userDAO.findById(id).orElseThrow(UserInexistentException::new);
    }

    @Override
    public void save(AbstractUser user) {
        userDAO.save(user);
    }

    @Override
    public void delete(Long id) {
        userDAO.deleteById(id);
    }

    @Override
    public void update(AbstractUser user) {
        userDAO.save(user);
    }
}
