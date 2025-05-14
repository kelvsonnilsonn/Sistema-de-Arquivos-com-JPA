package com.orizon.webdriver.domain.model.file;


import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.data.FileInformations;

import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.uinterface.AUserInterface;
import lombok.Getter;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public sealed abstract class AbstractFile implements AFileInterface permits VideoFile, GenericFile{

    @Getter
    private int id;

    private List<Comment> fileComments;

    @Autowired
    private List<Permission> filePermissions;

    private final FileInformations fileInformations;
    private AUserInterface user;

    public AbstractFile(FileInformations fileInformations){
        this.fileInformations = fileInformations;
        this.fileComments = new ArrayList<>();
    }

    @Override
    public void comment(Comment comment) {
        fileComments.add(comment);
    }

    public List<Comment> getComments() { return Collections.unmodifiableList(fileComments); }
    public List<Permission> getFilePermissions() { return new ArrayList<>(filePermissions); }

    public void getCommentsInFile(){

        if(!fileComments.isEmpty()){
            System.out.println("💬 Todos os comentários:");
            System.out.printf("%s",
                    fileComments.stream().map(Comment::toString)
                            .collect(Collectors.joining("\n")));
        } else {
            System.out.println("Nenhum comentário.");
        }
    }

    protected void addPermission(Permission permission) {
        filePermissions.add(permission);
        System.out.println(fileComments);

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
                📄 Nome: %s
                👤 Criador: %s
                🏷️ Tipo: %s
                📏 Tamanho: %d bytes
                📂 Endereço: %s
                🗓️ Lançamento: %s
                🔐 Permissões: %s
                
                🔗 URL: %s
                """,
                fileInformations.getFileName(),
                ((AbstractUser) user).getUserLogin(),
                this.getClass().getSimpleName(),
                fileInformations.getFileData() != null ? fileInformations.getFileData().getFileSize() : 0,
                fileInformations.getFileData() != null ?
                        fileInformations.getFileLocation() : "N/A",
                fileInformations.getFileData() != null ?
                        fileInformations.getFileData().getFileReleaseDate() : "N/A",
                filePermissions != null && !filePermissions.isEmpty() ?
                        filePermissions.stream()
                                .map(Permission::getDescription)
                                .collect(Collectors.joining(", ")) : "Nenhuma",
                fileInformations.getFileData() != null ?
                        fileInformations.getFileURL() : "N/A"
        );
    }
}