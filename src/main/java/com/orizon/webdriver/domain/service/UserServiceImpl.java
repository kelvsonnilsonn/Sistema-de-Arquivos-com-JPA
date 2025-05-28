package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.UserInexistentException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.Administrator;
import com.orizon.webdriver.domain.model.user.User;
import com.orizon.webdriver.domain.ports.service.UserService;
import com.orizon.webdriver.domain.valueobjects.UserAccess;
import com.orizon.webdriver.infra.persistence.repositories.CommentRepository;
import com.orizon.webdriver.infra.persistence.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@Getter
public class UserServiceImpl implements UserService{

    private final UserRepository userDAO;
    private final CommentRepository commentDAO;

    private AbstractUser currentUser;

    @Autowired
    public UserServiceImpl(UserRepository userDAO, CommentRepository commentDAO){
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
    }

    @Override
    public void create(String name, String email, String password, boolean isAdmin) {
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Objects.requireNonNull(email, () -> {throw new ENFieldException();});
        Objects.requireNonNull(password, () -> {throw new ENFieldException();});
        AbstractUser user;
        if(isAdmin){
            user = new Administrator(name, email, password);
        } else {
            user = new User(name, email, password);
        }
        userDAO.save(user);
    }

    @Override
    public void findAll() {
        userDAO.findAll().forEach(System.out::println);
    }

    @Override
    public AbstractUser findById(Long id) {
        return userDAO.findById(id).orElseThrow(UserInexistentException::new);
    }


    @Override
    public void delete(Long id) {
        AbstractUser user = findById(id);
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
        AbstractUser user = findById(id);
        user.setUsername(name);
        update(user);
    }

    @Override
    public void updateUserEmail(Long id, String email) {
        AbstractUser user = findById(id);
        UserAccess data = user.getUserAccess();
        String actualPassword = data.getPassword();
        try {
            user.setUserAccess(new UserAccess(email, actualPassword));
            update(user);
        } catch (IllegalArgumentException e){
            throw new RuntimeException("Problema ao mudar email.");
        }
    }

    @Override
    public void updateUserPassword(Long id, String password) {
        AbstractUser user = findById(id);
        UserAccess data = user.getUserAccess();
        String actualEmail = data.getEmail();
        try{
            user.setUserAccess(new UserAccess(actualEmail, password));
            update(user);
        } catch (IllegalArgumentException e){
            throw new RuntimeException("Problema ao mudar senha.");
        }
    }

    @Override
    public void promoteToAdmin(Long userId) {
        userDAO.unpromoteAdmin(userId);
    }

    @Override
    public void removeInstitutionFromUser(Long userId) {
        AbstractUser user = userDAO.findById(userId)
                .orElseThrow(UserInexistentException::new);

        user.setInstitution(null);
        update(user);
    }

    @Override
    public boolean login(String login, String password) {
        AbstractUser userOpt = userDAO.findByUsername(login);

        if (userOpt != null && userOpt.getUserAccess().getPassword().equals(password)) {
            this.currentUser = userOpt;
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        this.currentUser = null;
    }
}
