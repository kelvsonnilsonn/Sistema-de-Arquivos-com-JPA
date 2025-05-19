package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.FileOperation;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Support;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.valueobjects.UserAccess;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.LazyInitializationException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserAccess userAccess;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<AbstractFile> files = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Support> supportRequests = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<FileOperation> fileOperations = new HashSet<>();

    @Column(name = "creation_date")
    private Instant createdUserDate;

    protected AbstractUser(){}

    /*
     *  Construtor padrão
     *
     *  E-mail e Password já possuem verificadores na suas próprias implementações
     *  por isso não precisa verificar.
     */

    public AbstractUser(String login, String email, String password){
        if(login == null || login.isBlank()){
            throw new ENFieldException();
        }
        this.userAccess = new UserAccess(login, email, password);
        this.createdUserDate = Instant.now();
    }

    /*
     *   Métodos para adição e remoção de suportes no arquivo ↓
     */

    public boolean addSupportRequest(Support support){
        Objects.requireNonNull(support, () -> {throw new ENFieldException();});
        if(this.supportRequests.add(support)){
            support.setAuthor(this);
            return true;
        }
        return false;
    }

    public boolean removeSupportRequest(Support support){
        Objects.requireNonNull(support, () -> {throw new ENFieldException();});
        if(this.supportRequests.remove(support)){
            support.setAuthor(null);
            return true;
        }
        return false;
    }

    /*
     *   Métodos para adição e remoção de suportes no arquivo  ↑
     */

    /*
     *   Métodos para adição e remoção de comentários ↓
     */

    public boolean addComment(Comment comment) {
        Objects.requireNonNull(comment, "Comentário não pode ser nulo");

        if (this.comments.add(comment)) {
            comment.setAuthor(this);
            return true;
        }
        return false;
    }

    public boolean removeComment(Comment comment) {
        Objects.requireNonNull(comment, "Comentário não pode ser nulo");

        if (this.comments.remove(comment)) {
            comment.setAuthor(null);
            return true;
        }
        return false;
    }

    /*
     *   Métodos para adição e remoção de comentários ↑
     */

    /*
     *   Métodos para adição e remoção de arquivos ↓
     */

    public boolean addFile(AbstractFile file){
        Objects.requireNonNull(file, () -> {throw new ENFieldException();});
        if(this.files.add(file)){
            file.setUser(this);
            return true;
        }
        return false;
    }

    public boolean removeFile(AbstractFile file){
        Objects.requireNonNull(file, () -> {throw new ENFieldException();});
        if(this.files.remove(file)){
            file.setUser(null);
            return true;
        }
        return false;
    }

    /*
     *   Métodos para adição e remoção de arquivos ↑
     */

    public boolean addFileOperation(FileOperation operation){

        Objects.requireNonNull(operation, () -> {throw new ENFieldException();});
        if(this.fileOperations.add(operation)){
            operation.setUser(this);
            return true;
        }
        return false;

    }

    public String getUserLogin() { return this.userAccess.getLogin(); }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        return String.format(
                """
                🧑💻 Usuário: %s
                📧 Email: %s
                🆔 ID: %d
                🏢 Instituição: %s
                📅 Criado em: %s
                
                📂 Arquivos (%d):
               %s
                🆘 Solicitações de Suporte (%d):
               %s
                💬 Comentários (%d):
               %s
                ⚙️ Operações de Arquivo: (%d): 
               %s
               """,
                userAccess.getLogin(),
                userAccess.getEmail(),
                id,
                institution != null ? institution.getName() : "Não vinculado",
                dateFormatter.format(createdUserDate),

                // Seção de Arquivos
                files.size(),
                files.isEmpty() ? "  Nenhum arquivo vinculado" :
                        files.stream()
                                .map(f -> "  - " + f.getFileName() + " (" + f.getClass().getSimpleName() + ")" + " [ID: " + f.getId() + "]")
                                .collect(Collectors.joining("\n")),

                // Seção de Solicitações de Suporte
                supportRequests.size(),
                supportRequests.isEmpty() ? "  Nenhuma solicitação" :
                        supportRequests.stream()
                                .map(s -> "  - " +
                                        (s.getTitle() != null ? s.getTitle() : "Sem título") +
                                        " - Status: " + (s.isResolved() ? "✅ Resolvido" : "🟡 Pendente") + " [ID: " + s.getId() + "]")
                                .collect(Collectors.joining("\n")),

                // Seção de Comentários
                comments.size(),
                comments.isEmpty() ? "  Nenhum comentário" :
                        comments.stream()
                                .map(c -> "  - " +
                                        (c.getBody() != null ?
                                                (c.getBody().length() > 30 ?
                                                        c.getBody().substring(0, 30) + "..." + " [ID: " + c.getId() + "] " :
                                                        c.getBody() + " [ID: " + c.getId() + "] ") :
                                                "Sem conteúdo"))
                                .collect(Collectors.joining("\n")),

                // Seção de Operações de Arquivo
                fileOperations.size(),
                fileOperations.isEmpty() ? "  Nenhuma operação" :
                        fileOperations.stream()
                                .map(op -> "  - " + op.getOperationType() +
                                        " em " + dateFormatter.format(op.getOperationDate()) + " [ID: " + op.getId() + "]")
                                .collect(Collectors.joining("\n"))
        );
    }
}
