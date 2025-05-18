package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.SupportInexistentException;
import com.orizon.webdriver.domain.model.Support;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.infra.repositories.SupportRepository;
import com.orizon.webdriver.domain.ports.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SupportServiceImpl implements SupportService {

    private final SupportRepository supportDAO;

    @Autowired
    public SupportServiceImpl(SupportRepository supportDAO){
        this.supportDAO = supportDAO;
    }

    @Override
    public void addSupportRequest(AbstractUser user, AbstractFile file, String title, String body){
        Objects.requireNonNull(user, () -> {throw new ENFieldException();});
        Objects.requireNonNull(body, () -> {throw new ENFieldException();});
        Objects.requireNonNull(title, () -> {throw new ENFieldException();});
        Support support = new Support(user, file, title, body);
        user.addSupportRequest(support);
        save(support);
    }

    @Override
    public void listAll() {
        supportDAO.findAll().forEach(System.out::println);
    }

    @Override
    public Support findOne(Long id) {
        return supportDAO.findById(id).orElseThrow(SupportInexistentException::new);
    }

    @Override
    public void save(Support support) {
        supportDAO.save(support);
    }

    @Override
    public void delete(Long id) {
        supportDAO.deleteById(id);
    }

    @Override
    public void update(Support support) {
        supportDAO.save(support);
    }
}
