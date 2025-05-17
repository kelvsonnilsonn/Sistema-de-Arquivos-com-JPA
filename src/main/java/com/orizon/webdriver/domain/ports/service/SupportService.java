package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Support;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;

public interface SupportService {
    void listAll();
    Support findOne(Long id);
    void save(Support support);
    void delete(Long id);
    void update(Support support);
    void addSupportRequest(AbstractUser user, AbstractFile file, String title, String body);
}
