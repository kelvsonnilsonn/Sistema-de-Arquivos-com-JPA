package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Support;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.Administrator;

public interface SupportService {
    void listAll();
    Support findOne(Long id);
    void create(AbstractUser user, AbstractFile file, String title, String body);
    void delete(Long id);
    void update(Support support);
    void resolveSupportRequest(Long id, Administrator admin);
}
