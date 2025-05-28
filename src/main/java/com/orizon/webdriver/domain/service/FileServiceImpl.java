package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InexistentFileException;
import com.orizon.webdriver.domain.model.FileOperation;
import com.orizon.webdriver.domain.model.Permission;
import com.orizon.webdriver.domain.model.VersioningHistory;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.FileMetaData;
import com.orizon.webdriver.domain.model.file.GenericFile;
import com.orizon.webdriver.domain.model.file.VideoFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.ports.service.FileService;
import com.orizon.webdriver.infra.persistence.repositories.FileOperationsRepository;
import com.orizon.webdriver.infra.persistence.repositories.FilePermissionsRepository;
import com.orizon.webdriver.infra.persistence.repositories.FileRepository;
import com.orizon.webdriver.infra.persistence.repositories.VersioningHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepository fileDAO;
    private final FileOperationsRepository fileOperationsDAO;
    private final VersioningHistoryRepository versioningHistoryDAO;
    private final FilePermissionsRepository filePermissionsDAO;
    private final Scanner scan;

    @Autowired
    public FileServiceImpl(FileRepository fileDAO,  FileOperationsRepository fileOperationsDAO,
                           VersioningHistoryRepository versioningHistoryDAO, FilePermissionsRepository filePermissionsDAO,
                           Scanner scan){
        this.fileDAO = fileDAO;
        this.scan = scan;
        this.fileOperationsDAO = fileOperationsDAO;
        this.versioningHistoryDAO = versioningHistoryDAO;
        this.filePermissionsDAO = filePermissionsDAO;
    }

    @Override
    public void listAll() {
        fileDAO.findAll().forEach(System.out::println);
    }

    @Override
    public AbstractFile findOne(Long id) {
        return fileDAO.findById(id).orElseThrow(InexistentFileException::new);
    }

    @Override
    public void create(AbstractUser user, String filename, AbstractFile.FileType type) {
        Objects.requireNonNull(filename, () -> {throw new ENFieldException();});
        Objects.requireNonNull(type, () -> {throw new ENFieldException();});

        AbstractFile file;
        if (type == AbstractFile.FileType.VIDEO) {
            long duration = scan.nextLong();
            file = new VideoFile(filename, Duration.ofSeconds(duration));
        } else {
            file = new GenericFile(filename);
        }

        if(user.addFile(file)){
            fileDAO.save(file);
        }
    }

    @Override
    public void delete(Long id) {
        AbstractFile file = findOne(id);
        AbstractUser user = file.getUser();
        if(user.removeFile(file)){
            fileDAO.deleteById(id);
        }
    }

    @Override
    public void update(Long id, String name, FileOperation.OperationType type) {
        AbstractFile file = findOne(id);
        AbstractUser user = file.getUser();
        FileOperation operation = new FileOperation(file, user, type);

        if(user.addFileOperation(operation) && file.addOperation(operation)){
            file.setFileMetaData(new FileMetaData(name));
            String versionCommit = type.getDescription() + "o arquivo" + file.getFileName();
            versioningHistoryDAO.save(new VersioningHistory(user, file, versionCommit));
            fileOperationsDAO.save(operation);
            fileDAO.save(file);
        }
    }

    @Override
    public void shareFile(AbstractFile file, AbstractUser owner, AbstractUser receiver, Set<Permission.PermissionType> permissions){
        // Cria a operação de compartilhamento
        FileOperation operation = new FileOperation(file, owner, receiver);
        fileOperationsDAO.save(operation);
        // Adiciona as permissões
        for (Permission.PermissionType permission : permissions) {
            Permission permissionEntity = new Permission(file, receiver, permission);
            filePermissionsDAO.save(permissionEntity);
        }
    }

    @Override
    public Set<Permission.PermissionType> getUserPermissions(AbstractFile file, AbstractUser user) {
        Objects.requireNonNull(file, () -> {throw new ENFieldException();});
        Objects.requireNonNull(user, () -> {throw new ENFieldException();});

        return filePermissionsDAO.findByFileIdAndReceiverId(file.getId(), user.getId());
    }
}
