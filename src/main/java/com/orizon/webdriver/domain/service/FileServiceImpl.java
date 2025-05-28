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
import com.orizon.webdriver.domain.model.user.Administrator;
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
    public void findAll() {
        fileDAO.findAll().forEach(System.out::println);
    }

    @Override
    public AbstractFile findById(Long id) {
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
        AbstractFile file = findById(id);
        AbstractUser user = file.getUser();
        if(user.removeFile(file)){
            fileDAO.deleteById(id);
        }
    }

    @Override
    public void update(Long id, String name, FileOperation.OperationType type) {
        AbstractFile file = findById(id);
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

    /**
     * Procedimento para compartilhar um arquivo como outro usuário.
     *
     * @param file Arquivo que vai ser compartilhado.
     * @param owner Usuário que é o proprietário do arquivo.
     * @param receiver Usuário que vai receber o compartilhamento do arquivo.
     * @param permissions Conjunto de permissões que o usuário receptor terá sobre o arquivo (podem ser modificadas após a criação).
     *
     * @throws IllegalArgumentException Se o usuário não for o proprietário do arquivo
     * @throws ENFieldException Se o conjunto de permissões for nulo ou vazio.
     * @throws IllegalArgumentException Se o receptor for o próprio proprietário.
     * @throws RuntimeException Se o arquivo já foi compartilhado com o usuário receptor.
     *
     * @example
     * shareFile(file, owner, receiver, Set.of(Permission.PermissionType.READ, Permission.PermissionType.WRITE));
     * */
    @Override
    public void shareFile(AbstractFile file, AbstractUser owner, AbstractUser receiver, Set<Permission.PermissionType> permissions){

        if(!file.getUser().getId().equals(owner.getId())) {
            throw new IllegalArgumentException("O usuário não é o proprietário do arquivo.");
        }

        if(permissions == null || permissions.isEmpty()) {
            throw new ENFieldException("Permissões não podem ser nulas ou vazias.");
        }

        if(receiver.getId().equals(owner.getId())) {
            throw new IllegalArgumentException("Não é possível compartilhar o arquivo com o próprio proprietário.");
        }

        if(filePermissionsDAO.existsByFileIdAndReceiverId(file.getId(), receiver.getId())) {
            throw new RuntimeException("O arquivo já foi compartilhado com este usuário.");
        }

        // Cria a operação de compartilhamento
        FileOperation operation = new FileOperation(file, owner, receiver);
        fileOperationsDAO.save(operation);
        // Adiciona as permissões
        for (Permission.PermissionType permission : permissions) {
            Permission permissionEntity = new Permission(file.getId(), receiver.getId(), permission);
            filePermissionsDAO.save(permissionEntity);
        }
    }

    /**
     * Método para verificação de permissão do usuário para realizar operações em arquivos.
     * @param user Usuário que está tentando acessar o arquivo.
     *             <ul>
     *                 <li>Se for o proprietário do arquivo, retorna {@code true}.</li>
     *                 <li>Se for um administrador, também retorna {@code true} (tem acesso total).</li>
     *             </ul>
     * @param file Arquivo que está sendo acessado.
     * @param permission Tipo de permissão que está sendo verificada.
     * @return {@code true} se o usuário tiver permissão, {@code false} caso contrário.
     */

    @Override
    public boolean hasPermission(AbstractUser user, AbstractFile file, Permission.PermissionType permission) {
        if (file.getUser().equals(user)) {
            return true;
        }

        if (user instanceof Administrator) {
            return true;
        }

        // Verifica permissões específicas
        return filePermissionsDAO.findByFileIdAndReceiverId(file.getId(), user.getId()).contains(permission);
    }

    @Override
    public void addPermission(Long fileId, Long userId, Permission.PermissionType perm){
        Permission permission = new Permission(fileId, userId, perm);
        filePermissionsDAO.save(permission);
    }

    @Override
    public Set<Permission.PermissionType> getUserPermissions(Long fileId, Long userId) {
        return filePermissionsDAO.findByFileIdAndReceiverId(fileId, userId);
    }
}
