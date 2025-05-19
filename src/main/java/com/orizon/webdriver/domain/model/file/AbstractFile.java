package com.orizon.webdriver.domain.model.file;


import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.FileOperation;
import com.orizon.webdriver.domain.model.Support;
import com.orizon.webdriver.domain.model.VersioningHistory;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "file_type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractFile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Comment> fileComments = new HashSet<>();

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<VersioningHistory> versions = new HashSet<>();

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Support> supportRequests;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AbstractUser user;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<FileOperation> operations = new HashSet<>();

    @Autowired
    private List<Permission> filePermissions;

    @Embedded
    private FileMetaData fileMetaData;

    /*
     *  Construtor
     */
    protected AbstractFile(){}

    public AbstractFile(String fileMetaData){
        this.fileMetaData = new FileMetaData(fileMetaData);
    }

    /*
     *  Construtor
     */

    public String getFileName(){ return this.fileMetaData.getFileName(); }

    public boolean addComment(Comment comment) {
        Objects.requireNonNull(comment, () -> {throw new ENFieldException();});
        if(this.fileComments.add(comment)){
            comment.setFile(this);
            return true;
        }
        return false;
    }

    public boolean removeComment(Comment comment){
        Objects.requireNonNull(comment, () -> {throw new ENFieldException();});
        if(this.fileComments.remove(comment)){
            comment.setFile(null);
            return true;
        }
        return false;
    }

    public boolean addOperation(FileOperation operation) {
        Objects.requireNonNull(operation, () -> {throw new ENFieldException();});
        if(this.operations.add(operation)){
            operation.setFile(this);
            return true;
        }
        return false;
    }

//    public List<Permission> getFilePermissions() { return new ArrayList<>(filePermissions); }
//
//    public void getCommentsInFile(){
//
//        if(!fileComments.isEmpty()){
//            System.out.println("💬 Todos os comentários:");
//            System.out.printf("%s",
//                    fileComments.stream().map(Comment::toString)
//                            .collect(Collectors.joining("\n")));
//        } else {
//            System.out.println("Nenhum comentário.");
//        }
//    }
//
//    protected void addPermission(Permission permission) {
//        filePermissions.add(permission);
//        System.out.println(fileComments);
//
//    }
//
//    public String getFileName() { return this.fileMetaData.getFileName(); }
//    public void setFileName(String name) { this.fileMetaData.setFileName(name); }

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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZoneId.systemDefault());

        return String.format(
                """
                📄 Arquivo: %s (ID: %d)
                🏷️ Tipo: %s
                👤 Criado por: %s
                📏 Tamanho: %d bytes
                📂 Local: %s
                🗓️ Lançamento: %s
                🔗 URL: %s
                🔐 Permissões: %s
                
                💬 Comentários (%d):%s
                🔄 Versões (%d):%s
                🆘 Solicitações (%d):%s
                ⚙️ Operações (%d):%s
                """,
                // Informações básicas
                fileMetaData.getFileName(),
                this.getId(),
                this.getClass().getSimpleName(),
                user != null ? user.getUserLogin() : "N/A",
                fileMetaData.getFileSize(),
                fileMetaData.getFileLocation() != null ? fileMetaData.getFileLocation() : "N/A",
                fileMetaData.getFileReleaseDate() != null ?
                        dateFormatter.format(fileMetaData.getFileReleaseDate()) : "N/A",
                fileMetaData.getFileUrl() != null ? fileMetaData.getFileUrl() : "N/A",
                filePermissions != null && !filePermissions.isEmpty() ?
                        filePermissions.stream()
                                .map(Permission::getDescription)
                                .collect(Collectors.joining(", ")) : "Nenhuma",

                // Comentários
                fileComments.size(),
                fileComments.isEmpty() ? " Nenhum" :
                        fileComments.stream()
                                .map(c -> "\n   - [" + c.getId() + "] " +
                                        (c.getBody() != null ?
                                                (c.getBody().length() > 25 ?
                                                        c.getBody().substring(0, 25) + "..." :
                                                        c.getBody()) : "Sem conteúdo") +
                                        " (por " + (c.getAuthor() != null ? c.getAuthor().getUserLogin() : "N/A") + ")")
                                .collect(Collectors.joining()),

                // Versões
                versions.size(),
                versions.isEmpty() ? " Nenhuma" :
                        versions.stream()
                                .sorted(Comparator.comparing(VersioningHistory::getCreationDate).reversed())
                                .map(v -> "\n   - v" + v.getId() +
                                        " em " + dateFormatter.format(v.getCreationDate()) +
                                        (v.getCommitMessage() != null ?
                                                "\n     ↳ " + (v.getCommitMessage().length() > 40 ?
                                                        v.getCommitMessage().substring(0, 40) + "..." :
                                                        v.getCommitMessage()) : ""))
                                .collect(Collectors.joining()),

                // Solicitações
                supportRequests != null ? supportRequests.size() : 0,
                supportRequests == null || supportRequests.isEmpty() ? " Nenhuma" :
                        supportRequests.stream()
                                .map(s -> "\n   - [" + s.getId() + "] " +
                                        (s.getTitle() != null ? s.getTitle() : "Sem título") +
                                        " (" + (s.isResolved() ? "✅" : "🟡"))
                                .collect(Collectors.joining()),

                // Operações
                operations.size(),
                operations.isEmpty() ? " Nenhuma" :
                        operations.stream()
                                .sorted(Comparator.comparing(FileOperation::getOperationDate).reversed())
                                .map(op -> "\n   - " + op.getOperationType() +
                                        " por " + (op.getUser() != null ? op.getUser().getUserLogin() : "N/A") +
                                        " em " + dateTimeFormatter.format(op.getOperationDate()))
                                .collect(Collectors.joining())
        );
    }

    public enum FileType {
        TEXT, VIDEO, PHOTO
    }

}