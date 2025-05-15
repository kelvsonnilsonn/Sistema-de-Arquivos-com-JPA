package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.ports.file.FileOperations;

public interface SupportService {

    void addSupportRequest(AbstractUser user, String title, String body);
    void addSupportRequest(AbstractUser user, FileOperations file, String title, String body);
    void checkSupport(long id);
    void getAllSupports();
}
