package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FileRepository extends JpaRepository<AbstractFile, Long> {
    AbstractFile findByName(String name);
    AbstractFile findByNameAndUserId(String name, Long ownerId);
    Set<AbstractFile> findByUserId(Long userId);
}
