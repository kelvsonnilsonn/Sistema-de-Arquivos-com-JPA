package com.orizon.webdriver.domain.service.operation;

import com.orizon.webdriver.domain.exceptions.InvalidFileException;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.ports.repository.FileRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FileDeleter {
    public void delete(FileOperations file, FileRepository fileRepository){
        Objects.requireNonNull(file, "Arquivo a remover não pode ser nulo.");
        if(!(file instanceof AbstractFile)){
            throw new InvalidFileException();
        }

        fileRepository.delete(file);
    }
}
