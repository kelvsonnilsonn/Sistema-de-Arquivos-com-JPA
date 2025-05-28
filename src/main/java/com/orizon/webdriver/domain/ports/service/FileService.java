package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.FileOperation;
import com.orizon.webdriver.domain.model.Permission;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;

import java.util.Set;

public interface FileService {
    void findAll();
    AbstractFile findById(Long id);
    void create(AbstractUser user, String filename, AbstractFile.FileType type);
    void delete(Long id);
    void update(Long id, String name, FileOperation.OperationType type);
    void shareFile(AbstractFile file, AbstractUser owner, AbstractUser receiver, Set<Permission.PermissionType> permissions);
    Set<Permission.PermissionType> getUserPermissions(Long fileId, Long userId);
    void addPermission(Long fileId, Long userId, Permission.PermissionType perm);
    boolean hasPermission(AbstractUser user, AbstractFile file, Permission.PermissionType permission);
}
