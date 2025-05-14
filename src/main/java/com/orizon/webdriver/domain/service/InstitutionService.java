package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InstitutionLimitException;
import com.orizon.webdriver.domain.exceptions.InvalidInstitutionException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.infrastructure.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public Institution createInstitution(String name, String socialCause){
        Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        Objects.requireNonNull(socialCause, () -> {throw new ENFieldException();});
        Institution institution = new Institution(name, socialCause);
        institutionRepository.addInstitution(institution);
        return institution;
    }

    public void deleteInstitution(long id){
        Institution founded = institutionRepository.institutionSearch(id);
        if(founded == null){
            throw new InvalidInstitutionException();
        }

        institutionRepository.deleteInstitution(founded);
    }

    public void addInstitutionUser(Institution institution, AbstractUser user){
        Objects.requireNonNull(user, () -> {throw new ENFieldException();});
        Objects.requireNonNull(institution, () -> {throw new ENFieldException();});

        if(user.getInstitutionConection() != null){
            throw new InstitutionLimitException();
        }

        institution.addInstitutionUser(user);
        user.setInstitutionConection(institution);
    }
}
