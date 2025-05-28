package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FilePermissionsRepository extends JpaRepository<Permission, Long> {

    Set<Permission.PermissionType> findByFileIdAndReceiverId(Long fileId, Long userId);
}
