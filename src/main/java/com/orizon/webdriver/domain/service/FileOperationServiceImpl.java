package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.FileOperation;
import com.orizon.webdriver.domain.ports.service.FileOperationService;
import com.orizon.webdriver.infra.repositories.FileOperationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileOperationServiceImpl implements FileOperationService {

    private final FileOperationsRepository fileOperationDAO;

    public FileOperationServiceImpl(FileOperationsRepository fileOperationDAO){
        this.fileOperationDAO = fileOperationDAO;
    }

    @Override
    public void listAll() {
        fileOperationDAO.findAll().forEach(System.out::println);
    }

    @Override
    public List<FileOperation> findByFileId(Long id) {
        return fileOperationDAO.findByFileId(id);
    }

    @Override
    public List<FileOperation> findByUserId(Long id) {
        return fileOperationDAO.findByUserId(id);
    }

    @Override
    public void save(FileOperation operation) {
        fileOperationDAO.save(operation);
    }
}
