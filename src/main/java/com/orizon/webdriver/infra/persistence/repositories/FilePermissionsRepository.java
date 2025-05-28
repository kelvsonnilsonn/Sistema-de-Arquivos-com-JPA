package com.orizon.webdriver.infra.persistence.repositories;

import com.orizon.webdriver.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface FilePermissionsRepository extends JpaRepository<Permission, Long> {
    @Query("SELECT p.type FROM Permission p WHERE p.fileId = :fileId AND p.receiverId = :userId")
    Set<Permission.PermissionType> findByFileIdAndReceiverId(@Param("fileId") Long fileId, @Param("userId") Long userId);
    boolean existsByFileIdAndReceiverId(Long fileId, Long userId);
}
