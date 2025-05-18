package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InexistentFileException;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.GenericFile;
import com.orizon.webdriver.domain.model.file.VideoFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.ports.service.FileService;
import com.orizon.webdriver.infra.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.Scanner;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileDAO;
    private final Scanner scan;

    @Autowired
    public FileServiceImpl(FileRepository fileDAO, Scanner scan){
        this.fileDAO = fileDAO;
        this.scan = scan;
    }

    @Override
    public void listAll() {
        fileDAO.findAll().forEach(System.out::println);
    }

    @Override
    public AbstractFile findOne(Long id) {
        return fileDAO.findById(id).orElseThrow(InexistentFileException::new);
    }

    @Override
    public void create(AbstractUser user, String filename, AbstractFile.FileType type) {
        Objects.requireNonNull(filename, () -> {throw new ENFieldException();});
        Objects.requireNonNull(type, () -> {throw new ENFieldException();});

        AbstractFile file;
        if (type == AbstractFile.FileType.VIDEO) {
            long duration = scan.nextLong();
            file = new VideoFile(filename, Duration.ofSeconds(duration));
        } else {
            file = new GenericFile(filename);
        }

        if(user.addFile(file)){
            fileDAO.save(file);
        }
    }

    @Override
    public void delete(Long id) {
        fileDAO.deleteById(id);
    }

    @Override
    public void update(AbstractFile file) {
        fileDAO.save(file);
    }
}
