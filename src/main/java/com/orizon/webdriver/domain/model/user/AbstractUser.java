package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.exceptions.DupliquedFileException;
import com.orizon.webdriver.domain.exceptions.InexistentFileException;
import com.orizon.webdriver.domain.model.Support;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.valueobjects.UserAccess;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public sealed abstract class AbstractUser permits Administrator, User {

    @Getter
    private long id;
    private final UserAccess userAccess;
    @Getter
    private final Instant createdUserDate;
    @Getter
    private final List<FileOperations> files;
    @Getter
    private final List<Support> supportRequests;
    @Getter
    private Institution institutionConection;

    public List<FileOperations> getUserFiles() { return Collections.unmodifiableList(files); }

    public AbstractUser(String login, String email, String password){
        this.userAccess = new UserAccess(login, email, password);
        this.createdUserDate = Instant.now();
        this.files = new ArrayList<>();
        this.supportRequests = new ArrayList<>();
    }

    public void addSupportRequest(Support supportRequest){
        supportRequests.add(supportRequest);
    }

    public void checkSupportRequest(Support supportRequest){
        supportRequests.remove(supportRequest);
    }

    public String getUserLogin() { return this.userAccess.getLogin(); }

    public void addFile(FileOperations file){
        if(files.contains(file)){
            throw new DupliquedFileException();
        }
        files.add(file);
    }

    public void deleteFile(FileOperations file){
        if(!files.contains(file)){
            throw new InexistentFileException();
        }
        files.remove(file);
    }

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
                institutionConection != null ? institutionConection.getName() : "Não vinculado",
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
