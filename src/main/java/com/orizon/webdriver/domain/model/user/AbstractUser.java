package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.exceptions.DupliquedFileException;
import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Support;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.valueobjects.UserAccess;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private UserAccess userAccess;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AbstractFile> files = new HashSet<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Support> supportRequests = new HashSet<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @Column(name = "creation_date")
    private Instant createdUserDate;

    protected AbstractUser(){}

    public Set<AbstractFile> getUserFiles() { return new HashSet<>(files); }

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
    *   Permitido usar o bidirecional em AbstractUser graças ao POLIMORFISMO.
    */

    public void addSupportRequest(Support supportRequest){
        Objects.requireNonNull(supportRequest, () -> {throw new ENFieldException();});
        if(supportRequests.add(supportRequest)){
            supportRequest.setAuthor(this);
        }
    }

    public void addFile(AbstractFile file){
        Objects.requireNonNull(file, () -> {throw new ENFieldException();});
    }

    public String getUserLogin() { return this.userAccess.getLogin(); }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        return String.format(
                """
                🧑💻 Usuário: %s
                📧 E-mail: %s
                🆔 ID: %d
                🏢 Instituição: %s
                📅 Criado em: %s
                📂 Arquivos (%d):%s
                🆘 Solicitações de Suporte (%d):%s
                """,
                userAccess.getLogin(),
                userAccess.getEmail(),
                id,
                institution != null ? institution.getName() : "Não vinculado",
                dateFormatter.format(createdUserDate),
                files.size(),
                files.isEmpty() ? " Nenhum arquivo vinculado" :
                        files.stream()
                                .map(f -> "\n   - " + ((AbstractFile) f).getFileName() + " (" + f.getClass().getSimpleName() + ")")
                                .collect(Collectors.joining()),
                supportRequests.size(),
                supportRequests.isEmpty() ? " Nenhuma solicitação" :
                        supportRequests.stream()
                                .map(s -> "\n   - [" + s.getId() + "] " +
                                        (s.getTitle() != null ? s.getTitle() : "Sem título") +
                                        " - Status: " + (s.isResolved() ? "✅ Resolvido" : "🟡 Pendente"))
                                .collect(Collectors.joining())
        );
    }
}
