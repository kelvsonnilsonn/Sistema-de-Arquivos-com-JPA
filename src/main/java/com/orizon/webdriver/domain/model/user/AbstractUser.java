package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.exceptions.DupliquedFileException;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.user.uinterface.AUserInterface;
import com.orizon.webdriver.domain.model.user.userdata.UserAccess;

import com.orizon.webdriver.domain.service.UserService;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
public sealed abstract class AbstractUser implements AUserInterface permits Administrator, User {

    @Getter
    private long id;
    private final UserAccess userAccess;
    @Getter
    private final Instant createdUserDate;
    @Getter
    protected final List<AFileInterface> files;

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
}
