package com.orizon.webdriver.domain.ports.repository;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<AbstractFile, Long> {
}
