package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.VersionInexistentException;
import com.orizon.webdriver.domain.model.VersioningHistory;
import com.orizon.webdriver.infra.persistence.repositories.VersioningHistoryRepository;
import com.orizon.webdriver.domain.ports.service.VersionHistoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VersionHistoryServiceImpl implements VersionHistoryService {

    private final VersioningHistoryRepository versionHistoryDAO;

    @Autowired
    public VersionHistoryServiceImpl(VersioningHistoryRepository versionHistoryDAO){
        this.versionHistoryDAO = versionHistoryDAO;
    }

    @Override
    public void create(VersioningHistory version) {
        versionHistoryDAO.save(version);
    }

    @Override
    public void findAll() {
        versionHistoryDAO.findAll().forEach(System.out::println);
    }

    @Override
    public VersioningHistory findById(Long id) {
        return versionHistoryDAO.findById(id).orElseThrow(VersionInexistentException::new);
    }

    @Override
    public void updateVersionMessage(Long versionId, String newMessage) {
        VersioningHistory version = versionHistoryDAO.findById(versionId)
                .orElseThrow(() -> new ENFieldException("Versão não encontrada."));

        if (newMessage == null || newMessage.isBlank()) {
            throw new ENFieldException("Mensagem não pode ser nula ou vazia.");
        }

        version.setCommitMessage(newMessage);
        update(version);
    }

    @Override
    public void delete(Long id) {
        versionHistoryDAO.deleteById(id);
    }

    @Override
    public void update(VersioningHistory version) {
        versionHistoryDAO.save(version);
    }
}
