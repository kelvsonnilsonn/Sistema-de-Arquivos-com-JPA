package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.FileOperation;
import com.orizon.webdriver.infra.persistence.repositories.FileOperationsRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FileOperationService {

    private final FileOperationsRepository fileOperationDAO;

    public FileOperationService(FileOperationsRepository fileOperationDAO){
        this.fileOperationDAO = fileOperationDAO;
    }

    public void create(FileOperation operation) {
        fileOperationDAO.save(operation);
    }

    public void findAll() {
        fileOperationDAO.findAll().forEach(System.out::println);
    }

    public FileOperation findById(Long id){
        return fileOperationDAO.findById(id).orElseThrow();
    }

    public Set<FileOperation> findByFileId(Long id) {
        return fileOperationDAO.findByFileId(id);
    }

    public Set<FileOperation> findByUserId(Long id) {
        return fileOperationDAO.findByUserId(id);
    }

    public void update(FileOperation operation){
        fileOperationDAO.save(operation);
    }



}
