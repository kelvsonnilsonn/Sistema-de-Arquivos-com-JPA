package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.VersionInexistentException;
import com.orizon.webdriver.domain.model.VersioningHistory;
import com.orizon.webdriver.infra.repositories.VersioningHistoryRepository;
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
    public void listAll() {
        versionHistoryDAO.findAll().forEach(System.out::println);
    }

    @Override
    public VersioningHistory findOne(Long id) {
        return versionHistoryDAO.findById(id).orElseThrow(VersionInexistentException::new);
    }

    @Override
    public void save(VersioningHistory version) {
        versionHistoryDAO.save(version);
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
