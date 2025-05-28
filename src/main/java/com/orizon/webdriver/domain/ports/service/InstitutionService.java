package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Institution;

public interface InstitutionService {
    void findAll();
    Institution findById(Long id);
    void create(String nome, String socialCause);
    void delete(Long id);
    void update(Institution institution);
    void updateInstitutionName(Long id, String name);
    void updateInstitutionSocialCause(Long id, String socialCause);
    void updateAddress(Long institutionId, String zipcode, String street, String number,
                       String neighborhood, String city, String state,
                       String country, String complement);
}
