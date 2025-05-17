package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Institution;

public interface InstitutionService {
    void listAll();
    Institution findOne(Long id);
    void save(Institution file);
    void delete(Long id);
    void update(Institution institution);
}
