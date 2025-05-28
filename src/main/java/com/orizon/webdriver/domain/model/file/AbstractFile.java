package com.orizon.webdriver.domain.model.file;


import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidFileTypeException;
import com.orizon.webdriver.domain.model.*;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
    private Set<Support> supportRequests = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AbstractUser user;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<FileOperation> operations = new HashSet<>();

    @OneToMany(mappedBy = "fileId", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Permission> filePermissions = new HashSet<>();

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


    /*
     *   Métodos para adição e remoção de comentários no arquivo ↓
     */

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

    /*
     *   Métodos para adição e remoção de comentários no arquivo  ↑
     */

    /*
     *   Métodos para adição e remoção de suportes no arquivo ↓
     */

    public boolean addSupportRequest(Support support){
        Objects.requireNonNull(support, () -> {throw new ENFieldException();});
        if(this.supportRequests.add(support)){
            support.setFile(this);
            return true;
        }
        return false;
    }

    public boolean removeSupportRequest(Support support){
        Objects.requireNonNull(support, () -> {throw new ENFieldException();});
        if(this.supportRequests.remove(support)){
            support.setFile(null);
            return true;
        }
        return false;
    }

    /*
     *   Métodos para adição e remoção de suportes no arquivo  ↑
     */

    public boolean addOperation(FileOperation operation) {
        Objects.requireNonNull(operation, () -> {throw new ENFieldException();});
        if(this.operations.add(operation)){
            operation.setFile(this);
            return true;
        }
        return false;
    }

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
    public void addPermission(Permission permission) {
        filePermissions.add(permission);
    }
//
//    public String getFileName() { return this.fileMetaData.getFileName(); }
//    public void setFileName(String name) { this.fileMetaData.setFileName(name); }

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
                // Informações básicas (8 parâmetros)
                fileMetaData.getFileName(),
                this.getId(),
                this.getClass().getSimpleName(),
                user != null ? user.getUsername() : "N/A",
                fileMetaData.getFileSize(),
                fileMetaData.getFileLocation() != null ? fileMetaData.getFileLocation() : "N/A",
                fileMetaData.getFileReleaseDate() != null ? dateFormatter.format(fileMetaData.getFileReleaseDate()) : "N/A",
                fileMetaData.getFileUrl() != null ? fileMetaData.getFileUrl() : "N/A",
                filePermissions != null && !filePermissions.isEmpty() ?
                        filePermissions.stream()
                                .map(p -> p.getType().toString())
                                .collect(Collectors.joining(", ")) : "Nenhuma",

                // Comentários (2 parâmetros)
                fileComments.size(),
                fileComments.isEmpty() ? " Nenhum" :
                        fileComments.stream()
                                .map(c -> "\n   - " +
                                        (c.getBody() != null ?
                                                (c.getBody().length() > 25 ?
                                                        c.getBody().substring(0, 25) + "..." + " [ID: " + c.getId() + "] " :
                                                        c.getBody() + " [ID: " + c.getId() + "] ") : "Sem conteúdo") +
                                        " (por " + (c.getAuthor() != null ? c.getAuthor().getUsername() : "N/A") + ")")
                                .collect(Collectors.joining()),

                // Versões (2 parâmetros)
                versions.size(),
                versions.isEmpty() ? " Nenhuma" :
                        versions.stream()
                                .sorted(Comparator.comparing(VersioningHistory::getCreationDate).reversed())
                                .map(v -> "\n   - v" + v.getId() +
                                        " em " + dateTimeFormatter.format(v.getCreationDate()) +
                                        (v.getCommitMessage() != null ?
                                                "\n     ↳ " + (v.getCommitMessage().length() > 40 ?
                                                        v.getCommitMessage().substring(0, 40) + "..." :
                                                        v.getCommitMessage()) : ""))
                                .collect(Collectors.joining()),

                // Solicitações (2 parâmetros)
                supportRequests != null ? supportRequests.size() : 0,
                supportRequests == null || supportRequests.isEmpty() ? " Nenhuma" :
                        supportRequests.stream()
                                .map(s -> "\n   - " +
                                        (s.getTitle() != null ? s.getTitle() : "Sem título") +
                                        " (" + (s.isResolved() ? "✅" : s.getStatus() == Support.SupportStatus.PENDING ? "🟡 Pendente" : "Não vinculado a um administrador.") + ") [ID: " + s.getId() + "] ")
                                .collect(Collectors.joining()),

                // Operações (2 parâmetros)
                operations.size(),
                operations.isEmpty() ? " Nenhuma" :
                        operations.stream()
                                .sorted(Comparator.comparing(FileOperation::getOperationDate).reversed())
                                .map(op -> "\n   - " + op.getOperationType() +
                                        " por " + (op.getUser() != null ? op.getUser().getUsername() : "N/A") +
                                        " em " + dateTimeFormatter.format(op.getOperationDate()) + " [ID: " + op.getId() + "] ")
                                .collect(Collectors.joining())
        );
    }

    @Getter
    public enum FileType {
        TEXT(".txt"),
        VIDEO(".wav"),
        PHOTO(".jpg");

        private final String description;

        public static FileType from(String type){
            try{
                return FileType.valueOf(type);
            } catch (IllegalArgumentException e){
                throw new InvalidFileTypeException();
            }
        }

        FileType(String description){
            this.description = description;
        }
    }

}