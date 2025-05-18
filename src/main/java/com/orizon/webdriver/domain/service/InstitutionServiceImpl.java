package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.InvalidInstitutionException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.infra.repositories.InstitutionRepository;
import com.orizon.webdriver.domain.ports.service.InstitutionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionDAO;

    @Autowired
    public InstitutionServiceImpl(InstitutionRepository institutionDAO) {
        this.institutionDAO = institutionDAO;
    }

    @Override
    public void listAll() {
        institutionDAO.findAll().forEach(System.out::println);
    }

    @Override
    public Institution findOne(Long id) {
        return institutionDAO.findById(id).orElseThrow(InvalidInstitutionException::new);
    }

    @Override
    public void save(Institution institution){
        institutionDAO.save(institution);
    }

    @Override
    public void delete(Long id) {
        institutionDAO.deleteById(id);
    }

    @Override
    public void update(Institution institution) {
        institutionDAO.save(institution);
    }
}
