package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.ports.file.FileOperations;

import com.orizon.webdriver.domain.ports.repository.FileRepository;
import com.orizon.webdriver.domain.ports.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final List<AbstractFile.Permission> initialPermission;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, List<AbstractFile.Permission> initialPermission){
        this.fileRepository = fileRepository;
        this.initialPermission = initialPermission;
    }

    public void delete(FileOperations file){
        fileRepository.delete(file);
    }

    public FileOperations create(String type){
        return fileRepository.create(type, initialPermission);
    }

    public void addComment(FileOperations file, String comment){
        file.comment(new Comment(comment));
    }

    public void update(FileOperations file){
        fileRepository.update(file);
    }

    public void search(int id){
        fileRepository.search(id);
    }

    public List<FileOperations> search(String fileName){
        return fileRepository.search(fileName);
    }

    public void search(FileOperations file){
        fileRepository.search(file);
    }
}
