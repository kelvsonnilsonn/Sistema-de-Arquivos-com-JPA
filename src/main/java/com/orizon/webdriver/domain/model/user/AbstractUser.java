package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.exceptions.DupliquedFileException;
import com.orizon.webdriver.domain.exceptions.InexistentFileException;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.institution.Institution;
import com.orizon.webdriver.domain.model.user.userdata.UserAccess;

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
    protected final List<AFileInterface> files;
    @Getter
    private Institution institutionConection;

    public List<AFileInterface> getUserFiles() { return Collections.unmodifiableList(files); }

    public AbstractUser(String login, String email, String password){
        this.userAccess = new UserAccess(login, email, password);
        this.createdUserDate = Instant.now();
        this.files = new ArrayList<>();
    }

    public String getUserLogin() { return this.userAccess.getLogin(); }

    public void addFile(AFileInterface file){
        if(files.contains(file)){
            throw new DupliquedFileException();
        }
        files.add(file);
    }

    public void deleteFile(AFileInterface file){
        if(!files.contains(file)){
            throw new InexistentFileException();
        }
        files.remove(file);
    }

    @Override
    public String toString() {
        return String.format(
                """
                🧑💻 Usuário: %s
                📧 E-mail: %s
                🆔 ID: %d
                🏢 Instituição: %s
                📅 Criado em: %s
                📂 Arquivos (%d):%s
                """,
                userAccess.getLogin(),
                userAccess.getEmail(),
                id,
                institutionConection != null ? institutionConection.getName() : "Não vinculado",
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                        .format(createdUserDate),
                files.size(),
                files.isEmpty() ? " Nenhum arquivo vinculado" :
                        files.stream()
                                .map(f -> "\n   - " + ((AbstractFile) f).getFileName() + " (" + f.getClass().getSimpleName() + ")")
                                .collect(Collectors.joining())
        );
    }
}
