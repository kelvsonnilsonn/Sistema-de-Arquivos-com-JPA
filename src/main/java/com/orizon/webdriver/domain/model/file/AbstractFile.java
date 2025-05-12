package com.orizon.webdriver.domain.model.file;


import com.orizon.webdriver.domain.model.file.filedatas.FileInformations;

import lombok.Getter;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class AbstractFile implements AFileInterface{
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

    @Getter
    public enum FileType{
        TEXT(".txt"),
        VIDEO(".wav"),
        PHOTO(".jpg");

        private final String description;

        FileType(String description){
            this.description = description;
        }
    }
}