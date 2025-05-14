package com.orizon.webdriver.domain.model.user;

import com.orizon.webdriver.domain.exceptions.DupliquedFileException;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.user.userdata.UserAccess;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Setter
public sealed abstract class AbstractUser permits Administrator, User {

    @Getter
    private long id;
    private UserAccess userAccess;
    @Getter
    private final Date createdUserDate;
    @Getter
    private List<AFileInterface> files;

    public AbstractUser(String login, String email, String password){
        this.userAccess = new UserAccess(login, email, password);
        this.createdUserDate = Date.valueOf(LocalDate.now());
    }

//    public void addFile(AFileInterface file){
//        if(files.contains(file)){
//            throw new DupliquedFileException();
//        }
//        files.add(file);
//    }
}
