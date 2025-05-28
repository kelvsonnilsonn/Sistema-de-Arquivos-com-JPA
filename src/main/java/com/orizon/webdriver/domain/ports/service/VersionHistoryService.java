package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.VersioningHistory;

import java.util.Set;

public interface VersionHistoryService {
    void findAll();
    VersioningHistory findById(Long id);
    void create(VersioningHistory version);
    void delete(Long id);
    void update(VersioningHistory version);
    void updateVersionMessage(Long versionId, String newMessage);
    Set<VersioningHistory> findByFileId(Long fileId);
}
