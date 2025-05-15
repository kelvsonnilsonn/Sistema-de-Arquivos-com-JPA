package com.orizon.webdriver.domain.ports.repository;

import com.orizon.webdriver.domain.model.Institution;

public interface InstitutionRepository {
    void getAllInstitutions();
    void addInstitution(Institution institution);
    void deleteInstitution(Institution institution);
    Institution institutionSearch(long id);
}
