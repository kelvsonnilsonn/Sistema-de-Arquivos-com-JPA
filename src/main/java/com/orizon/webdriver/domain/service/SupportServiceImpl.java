package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.Support;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.ports.repository.SupportRepository;
import com.orizon.webdriver.domain.ports.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SupportServiceImpl implements SupportService {

    private final SupportRepository supportRepository;

    @Autowired
    public SupportServiceImpl(SupportRepository supportRepository){
        this.supportRepository = supportRepository;
    }

    @Override
    public void addSupportRequest(AbstractUser user,String title, String body){
        Objects.requireNonNull(user, () -> {throw new ENFieldException();});
        Objects.requireNonNull(body, () -> {throw new ENFieldException();});
        Objects.requireNonNull(title, () -> {throw new ENFieldException();});
        Support support = new Support(user, title, body);
        user.addSupportRequest(support);
        supportRepository.addSupport(support);
    }

    @Override
    public void addSupportRequest(AbstractUser user, FileOperations file, String title, String body){
        Objects.requireNonNull(user, () -> {throw new ENFieldException();});
        Objects.requireNonNull(body, () -> {throw new ENFieldException();});
        Objects.requireNonNull(title, () -> {throw new ENFieldException();});
        Support support = new Support(user, title, body);
        user.addSupportRequest(support);
        supportRepository.addSupport(support);
    }

    @Override
    public void checkSupport(long id){
        supportRepository.checkSupport(id);
    }

    @Override
    public void getAllSupports(){
        supportRepository.getAllSupports();
    }
}
