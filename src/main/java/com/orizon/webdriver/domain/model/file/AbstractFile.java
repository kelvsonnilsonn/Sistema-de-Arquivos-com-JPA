package com.orizon.webdriver.domain.model.file;


import com.orizon.webdriver.domain.model.file.filedatas.FileInformations;

import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import lombok.Getter;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class AbstractFile implements AFileInterface {
    private int id;
    @Autowired
    private List<Permission> filePermissions;
    private final FileInformations fileInformations;

    public AbstractFile(FileInformations fileInformations){
        this.fileInformations = fileInformations;
    }

    public List<Permission> getFilePermissions() { return new ArrayList<>(filePermissions); }

    protected void addPermission(Permission permission) {
        filePermissions.add(permission);
    }

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
                Tipo: %s
                Tamanho: %d
                Endereço: %s
                Lançamento: %s
                Permissões: %s
                URL: %s
                """,
                fileInformations.getFileName(),
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