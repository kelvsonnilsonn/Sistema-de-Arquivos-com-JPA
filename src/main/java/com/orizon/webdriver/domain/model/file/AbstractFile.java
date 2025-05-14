package com.orizon.webdriver.domain.model.file;


import com.orizon.webdriver.domain.model.file.data.FileInformations;

import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.uinterface.AUserInterface;
import lombok.Getter;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Setter
public sealed abstract class AbstractFile implements AFileInterface permits VideoFile, GenericFile{
    @Getter
    private int id;
    @Autowired
    private List<Permission> filePermissions;
    private final FileInformations fileInformations;
    private AUserInterface user;

    public AbstractFile(FileInformations fileInformations){
        this.fileInformations = fileInformations;
    }

    public List<Permission> getFilePermissions() { return new ArrayList<>(filePermissions); }

    protected void addPermission(Permission permission) {
        filePermissions.add(permission);
    }

    public String getFileName() { return this.fileInformations.getFileName(); }
    public void setFileName(String name) { this.fileInformations.setFileName(name); }

    @Getter
    public enum Permission{
        SAVE("Salvar"),
        DELETE("Deletar"),
        LOAD("Carregar"),
        EDIT("Editar");

        private final String description;

        Permission(String description) {
            this.description = description;
        }
    }

    @Override
    public String toString() {
        return String.format(
                """
                Nome: %s
                Criador: %s
                Tipo: %s
                Tamanho: %d
                Endereço: %s
                Lançamento: %s
                Permissões: %s
                URL: %s
                """,
                fileInformations.getFileName(),
                ((AbstractUser) user).getUserLogin(),
                this.getClass().getSimpleName(), // Mostra "GenericFile" ou "VideoFile"
                fileInformations.getFileData() != null ? fileInformations.getFileData().getFileSize() : 0,
                fileInformations.getFileData() != null ?
                        fileInformations.getFileData().getFileAddress().getFileLocation() : "N/A",
                fileInformations.getFileData() != null ?
                        fileInformations.getFileData().getFileReleaseDate() : "N/A",
                filePermissions != null ? filePermissions : "Nenhuma",
                fileInformations.getFileData() != null ?
                        fileInformations.getFileData().getFileAddress().getFileUrl() : "N/A"

        );
    }
}