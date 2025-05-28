package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.SupportInexistentException;
import com.orizon.webdriver.domain.model.Support;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.Administrator;
import com.orizon.webdriver.infra.persistence.repositories.SupportRepository;
import com.orizon.webdriver.domain.ports.service.SupportService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
@Transactional
public class SupportServiceImpl implements SupportService {

    private final SupportRepository supportDAO;

    @Autowired
    public SupportServiceImpl(SupportRepository supportDAO){
        this.supportDAO = supportDAO;
    }

    @Override
    public void create(AbstractUser user, AbstractFile file, String title, String body){
        Objects.requireNonNull(user, () -> {throw new ENFieldException();});
        Objects.requireNonNull(body, () -> {throw new ENFieldException();});
        Objects.requireNonNull(title, () -> {throw new ENFieldException();});
        Support support = new Support(user, file, title, body);
        if(user.addSupportRequest(support) && file.addSupportRequest(support)){
            supportDAO.save(support);
        }
    }

    @Override
    public void findAll() {
        supportDAO.findAll().forEach(System.out::println);
    }

    @Override
    public Support findById(Long id) {
        return supportDAO.findById(id).orElseThrow(SupportInexistentException::new);
    }



    @Override
    public void delete(Long id) {
        AbstractFile file = findById(id).getFile();
        AbstractUser user = findById(id).getAuthor();
        Support support = findById(id);
        if(user.removeSupportRequest(support) && file.removeSupportRequest(support)){
            supportDAO.deleteById(id);
        }
    }

    @Override
    public void update(Support support) {
        Objects.requireNonNull(support, () -> {throw new ENFieldException();});
        supportDAO.save(support);
    }

    @Override
    public void assignAdminToSupport(Long supportId, AbstractUser admin){
        if(!(admin instanceof Administrator)) {
            throw new ENFieldException("Somente admins podem ser linkados a pedidos de suporte.");
        }

        Support support = supportDAO.findById(supportId).orElseThrow();
        support.setAdmin((Administrator) admin);
        support.setStatus(Support.SupportStatus.PENDING);
        update(support);
    }

    @Override
    public void resolveSupport(Long supportId, AbstractUser admin){
        if(!(admin instanceof Administrator)) {
            throw new ENFieldException("Somente admins podem resolver pedidos de suporte.");
        }

        Support support = supportDAO.findById(supportId).orElseThrow();
        ((Administrator) admin).resolveSupportRequest(support);
        support.setStatus(Support.SupportStatus.RESOLVED);
        support.setResolvedDate(Instant.now());
        update(support);
    }
}
