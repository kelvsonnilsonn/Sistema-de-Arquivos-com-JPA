package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.model.user.AbstractUser;
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

    public FileOperations create(String type, AbstractUser user){
        FileOperations file = fileService.create(type);
        ((AbstractFile) file).setUser(user);
        user.addFile(file);
        logger.log(file, LogService.LogType.CREATE);
        return file;
    }

    public void delete(FileOperations file, AbstractUser user){
        fileService.delete(file);
        user.deleteFile(file);
        logger.log(file, LogService.LogType.DELETE);
    }

    public void edit(FileOperations file){
        fileService.update(file);
        logger.log(file, LogService.LogType.UPDATE);
    }

    public void comment(FileOperations file, String comment){
        fileService.addComment(file, comment);
        logger.log(new Comment(comment));
    }

}
