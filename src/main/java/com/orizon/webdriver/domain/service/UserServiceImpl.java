package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.ports.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final FileServiceImpl fileService;
    private final LogServiceImpl logger;

    @Autowired
    public UserServiceImpl(FileServiceImpl fileService, LogServiceImpl logger){
        this.fileService = fileService;
        this.logger = logger;
    }

    @Override
    public FileOperations create(String type, AbstractUser user){
        FileOperations file = fileService.create(type);
        ((AbstractFile) file).setUser(user);
        user.addFile(file);
        logger.log(file, LogServiceImpl.LogType.CREATE);
        return file;
    }

    @Override
    public void delete(FileOperations file, AbstractUser user){
        fileService.delete(file);
        user.deleteFile(file);
        logger.log(file, LogServiceImpl.LogType.DELETE);
    }

    @Override
    public void edit(FileOperations file){
        fileService.update(file);
        logger.log(file, LogServiceImpl.LogType.UPDATE);
    }

    @Override
    public void comment(FileOperations file, String comment){
        fileService.addComment(file, comment);
        logger.log(new Comment(comment));
    }

}
