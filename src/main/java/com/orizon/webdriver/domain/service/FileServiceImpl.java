package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.exceptions.InexistentFileException;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.infra.repositories.FileRepository;
import com.orizon.webdriver.domain.ports.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileDAO;

    @Autowired
    public FileServiceImpl(FileRepository fileDAO){
        this.fileDAO = fileDAO;
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
    public void save(AbstractFile file) {
        fileDAO.save(file);
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
