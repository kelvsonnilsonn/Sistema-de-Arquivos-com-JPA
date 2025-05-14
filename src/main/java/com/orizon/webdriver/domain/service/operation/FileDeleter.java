package com.orizon.webdriver.domain.service.operation;

import com.orizon.webdriver.domain.exceptions.InvalidFileException;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.repository.FileRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FileDeleter {
    public void delete(AFileInterface file, FileRepository fileRepository){
        Objects.requireNonNull(file, "Arquivo a remover não pode ser nulo.");
        if(!(file instanceof AbstractFile)){
            throw new InvalidFileException();
        }

        file.delete(fileRepository);
    }
}
