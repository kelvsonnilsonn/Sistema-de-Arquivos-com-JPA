package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.user.AbstractUser;

public interface InstitutionService {

    Institution createInstitution(String name, String socialCause);
    void deleteInstitution(long id);
    void addInstitutionUser(Institution institution, AbstractUser user);
}
