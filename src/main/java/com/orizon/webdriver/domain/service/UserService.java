package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.uinterface.AUserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final FileService fileService;
    private final LogService logger;

    @Autowired
    public UserService(FileService fileService, LogService logger){
        this.fileService = fileService;
        this.logger = logger;
    }

    public AFileInterface create(String type, AbstractUser user){
        AFileInterface file = fileService.create(type);
        ((AbstractFile) file).setUser(user);
        user.addFile(file);
        logger.log(file, LogService.LogType.CREATE);
        return file;
    }

    public void delete(AFileInterface file, AbstractUser user){
        fileService.delete(file);
        user.deleteFile(file);
        logger.log(file, LogService.LogType.DELETE);
    }

    public void edit(AFileInterface file){
        fileService.update(file);
        logger.log(file, LogService.LogType.UPDATE);
    }

}
