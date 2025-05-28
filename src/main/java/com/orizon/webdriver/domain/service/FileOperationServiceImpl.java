package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.FileOperation;
import com.orizon.webdriver.domain.ports.service.FileOperationService;
import com.orizon.webdriver.infra.persistence.repositories.FileOperationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FileOperationServiceImpl implements FileOperationService {

    private final FileOperationsRepository fileOperationDAO;

    public FileOperationServiceImpl(FileOperationsRepository fileOperationDAO){
        this.fileOperationDAO = fileOperationDAO;
    }

    @Override
    public void create(FileOperation operation) {
        fileOperationDAO.save(operation);
    }

    @Override
    public void findAll() {
        fileOperationDAO.findAll().forEach(System.out::println);
    }

    public FileOperation findById(Long id){
        return fileOperationDAO.findById(id).orElseThrow();
    }

    @Override
    public Set<FileOperation> findByFileId(Long id) {
        return fileOperationDAO.findByFileId(id);
    }

    @Override
    public Set<FileOperation> findByUserId(Long id) {
        return fileOperationDAO.findByUserId(id);
    }

    public void update(FileOperation operation){
        fileOperationDAO.save(operation);
    }



}
