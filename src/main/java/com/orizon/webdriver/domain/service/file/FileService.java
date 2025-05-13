package com.orizon.webdriver.domain.service.file;

import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.exceptions.InvalidFileException;
import com.orizon.webdriver.domain.model.file.fenum.FileType;

import com.orizon.webdriver.domain.service.factory.FileFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {

    private final List<AFileInterface> files;
    private final FileFactory fileFactory;

    @Autowired
    public FileService(FileFactory fileFactory){
        this.files = new ArrayList<>();
        this.fileFactory = fileFactory;
    }

    public void processFiles(){     // Carregar arquivos no banco
        files.forEach(AFileInterface::save);
        files.stream().map(AFileInterface::load).forEach(files::add);
    }

    public void deleteFile(AFileInterface file){
        Objects.requireNonNull(file, "Arquivo a remover não pode ser nulo.");
        if(!(file instanceof AbstractFile)){
            throw new InvalidFileException();
        }

        files.remove(file);
        file.deleteFile();
    }

    public void createFile(String type){
        System.out.println(fileFactory.create(FileType.from(type.toUpperCase())));
    }
}
