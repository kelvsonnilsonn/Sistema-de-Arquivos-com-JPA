package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.VersioningHistory;

public interface VersionHistoryService {
    void listAll();
    VersioningHistory findOne(Long id);
    void save(VersioningHistory version);
    void delete(Long id);
    void update(VersioningHistory version);
}
