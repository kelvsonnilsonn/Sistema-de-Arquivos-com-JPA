package com.orizon.webdriver.domain.model;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
public class Support {
    @Setter
    private long id;
    private long authorId;
    private String author;
    private String title;
    private String body;
    private boolean status = false;
    private FileOperations file;

    public Support(AbstractUser user, String title,String body){
        this.authorId = Objects.requireNonNull(user, () -> { throw new ENFieldException();}).getId();
        this.author = user.getUserLogin();
        this.title = Objects.requireNonNull(title, () -> {throw new ENFieldException();});
        this.body = Objects.requireNonNull(body, () -> {throw new ENFieldException();});
    }

    public Support(AbstractUser user, String title,String body, FileOperations file){
        this.authorId = Objects.requireNonNull(user, () -> { throw new ENFieldException();}).getId();
        this.author = user.getUserLogin();
        this.title = Objects.requireNonNull(title, () -> {throw new ENFieldException();});
        this.body = Objects.requireNonNull(body, () -> {throw new ENFieldException();});
        this.file = file;
    }

    public void changeSupportStatus(){ this.status = !status; }

    public boolean isResolved() { return status; }

    @Override
    public String toString() {
        return String.format(
                """
                🆔 ID do Suporte: %d
                ✍️ Autor: %s (ID: %d)
                📝 Mensagem: 
                   "%s"
                🔄 Status: %s
                """,
                id,
                author,
                authorId,
                body,
                status ? "✅ Resolvido" : "🟡 Pendente"
        );
    }
}
