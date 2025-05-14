package com.orizon.webdriver.domain.model.file;


import com.orizon.webdriver.domain.model.Comment;

import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import lombok.Getter;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public sealed abstract class AbstractFile implements FileOperations permits VideoFile, GenericFile{

    @Getter
    private int id;

    private List<Comment> fileComments;

    @Autowired
    private List<Permission> filePermissions;

    private final FileMetaData fileMetaData;
    private AbstractUser user;

    public AbstractFile(FileMetaData fileMetaData){
        this.fileMetaData = fileMetaData;
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

    public String getFileName() { return this.fileMetaData.getFileName(); }
    public void setFileName(String name) { this.fileMetaData.setFileName(name); }

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
                fileMetaData.getFileName(),
                user != null ? user.getUserLogin() : "N/A",
                this.getClass().getSimpleName(),
                fileMetaData.getFileSize(),
                fileMetaData.getFileLocation() != null ? fileMetaData.getFileLocation() : "N/A",
                fileMetaData.getFileReleaseDate() != null ?
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                .format(fileMetaData.getFileReleaseDate().toInstant()
                                        .atZone(ZoneId.systemDefault())) : "N/A",
                filePermissions != null && !filePermissions.isEmpty() ?
                        filePermissions.stream()
                                .map(Permission::getDescription)
                                .collect(Collectors.joining(", ")) : "Nenhuma",
                fileMetaData.getFileUrl() != null ? fileMetaData.getFileUrl() : "N/A"
        );
    }
}