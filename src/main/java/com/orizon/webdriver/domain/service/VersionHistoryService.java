package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.VersionInexistentException;
import com.orizon.webdriver.domain.model.VersioningHistory;
import com.orizon.webdriver.infra.persistence.repositories.VersioningHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Transactional
public class VersionHistoryService {

    private final VersioningHistoryRepository versionHistoryDAO;

    @Autowired
    public VersionHistoryService(VersioningHistoryRepository versionHistoryDAO){
        this.versionHistoryDAO = versionHistoryDAO;
    }

    public void create(VersioningHistory version) {
        versionHistoryDAO.save(version);
    }

    public void findAll() {
        versionHistoryDAO.findAll().forEach(System.out::println);
    }

    public VersioningHistory findById(Long id) {
        return versionHistoryDAO.findById(id).orElseThrow(VersionInexistentException::new);
    }

    public Set<VersioningHistory> findByFileId(Long fileId){
        return versionHistoryDAO.findByFileId(fileId);
    }

    public void updateVersionMessage(Long versionId, String newMessage) {
        VersioningHistory version = versionHistoryDAO.findById(versionId)
                .orElseThrow(() -> new ENFieldException("Versão não encontrada."));

        if (newMessage == null || newMessage.isBlank()) {
            throw new ENFieldException("Mensagem não pode ser nula ou vazia.");
        }

        version.setCommitMessage(newMessage);
        update(version);
    }

    public void delete(Long id) {
        versionHistoryDAO.deleteById(id);
    }

    public void update(VersioningHistory version) {
        versionHistoryDAO.save(version);
    }
}
