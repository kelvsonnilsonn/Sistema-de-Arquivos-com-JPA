package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.FileOperation;
import com.orizon.webdriver.domain.model.Permission;
import com.orizon.webdriver.domain.model.VersioningHistory;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;

import java.util.Set;

public interface FileService {
    void findAll();
    AbstractFile findById(Long id);
    void create(AbstractFile file, AbstractUser user);
    void delete(Long id);
    void shareFile(AbstractFile file, AbstractUser owner, AbstractUser receiver, Set<Permission.PermissionType> permissions);
    Set<Permission.PermissionType> getUserPermissions(Long fileId, Long userId);
    void addPermission(Long fileId, Long userId, Permission.PermissionType perm);
    boolean hasPermission(AbstractUser user, AbstractFile file, Permission.PermissionType permission);
    AbstractFile findByName(String name);
    AbstractFile findByNameAndOwnerId(String name, Long ownerId);
    Set<AbstractFile> findByUserId(Long userId);
    void update(AbstractFile file, VersioningHistory version, FileOperation.OperationType type);
    Set<AbstractFile> findVisibleFiles(AbstractUser user);
}
