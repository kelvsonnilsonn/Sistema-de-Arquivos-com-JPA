package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.user.AbstractUser;

public interface UserService {
    void findAll();
    AbstractUser findById(Long id);
    void create(String name, String email, String password, boolean isAdmin);
    void delete(Long id);
    void update(AbstractUser user);
    void updateUserName(Long id, String name);
    void updateUserEmail(Long id, String email);
    void updateUserPassword(Long id, String password);
    boolean login(String login, String password);
    void logout();
    void promoteToAdmin(Long userId);
    void removeInstitutionFromUser(Long userId);
    AbstractUser getCurrentUser();
    AbstractUser findByUsername(String username);
}
