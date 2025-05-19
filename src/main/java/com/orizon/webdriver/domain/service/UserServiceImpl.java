package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.ShortPasswordException;
import com.orizon.webdriver.domain.exceptions.UserInexistentException;
import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.Administrator;
import com.orizon.webdriver.domain.model.user.User;
import com.orizon.webdriver.domain.valueobjects.UserAccess;
import com.orizon.webdriver.infra.repositories.UserRepository;
import com.orizon.webdriver.domain.ports.service.CommentService;
import com.orizon.webdriver.domain.ports.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
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
    public void create(String name, String email, String password, String type) {
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Objects.requireNonNull(email, () -> {throw new ENFieldException();});
        Objects.requireNonNull(password, () -> {throw new ENFieldException();});
        Objects.requireNonNull(type, () -> {throw new ENFieldException();});
        AbstractUser user;
        if(type.equalsIgnoreCase("admin")){
            user = new Administrator(name, email, password);
        } else {
            user = new User(name, email, password);
        }
        userDAO.save(user);
    }

    @Override
    public void delete(Long id) {
        AbstractUser user = findOne(id);
        Institution institution = user.getInstitution();
        if(institution != null){
            user.setInstitution(null);
            institution.removeConsumer(user);
        }
        userDAO.deleteById(id);
    }

    @Override
    public void update(AbstractUser user) {
        Objects.requireNonNull(user, () -> {throw new ENFieldException();});
        userDAO.save(user);
    }

    @Override
    public void updateUserName(Long id, String name) {
        Objects.requireNonNull(id, () -> {throw new ENFieldException();});
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        if(name.isBlank()){
            throw new RuntimeException("Problema ao mudar nome.");
        }
        AbstractUser user = findOne(id);
        UserAccess data = user.getUserAccess();
        String actualEmail = data.getEmail();
        String actualPassword = data.getPassword();
        user.setUserAccess(new UserAccess(name, actualEmail, actualPassword));
        update(user);
    }

    @Override
    public void updateUserEmail(Long id, String email) {
        AbstractUser user = findOne(id);
        UserAccess data = user.getUserAccess();
        String name = data.getLogin();
        String actualPassword = data.getPassword();
        try {
            user.setUserAccess(new UserAccess(name, email, actualPassword));
            update(user);
        } catch (IllegalArgumentException e){
            throw new RuntimeException("Problema ao mudar email.");
        }
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        AbstractUser user = findOne(id);
        UserAccess data = user.getUserAccess();
        String actualName = data.getLogin();
        String actualEmail = data.getEmail();
        try{
            user.setUserAccess(new UserAccess(actualName, actualEmail, password));
            update(user);
        } catch (IllegalArgumentException e){
            throw new RuntimeException("Problema ao mudar senha.");
        }
    }
}
