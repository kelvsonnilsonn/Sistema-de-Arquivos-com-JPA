package com.orizon.webdriver.domain.model.file;

import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.infrastructure.repository.FileRepository;
import lombok.Setter;

@Setter
public final class GenericFile extends AbstractFile{

    public GenericFile(FileMetaData fileMetaData) {
        super(fileMetaData);
    }

    @Override
    public FileOperations load() {
        return this;
    }

    @Override
    public void save(FileRepository fileRepository){

    }

    @Override
    public void delete(FileRepository fileRepository) {

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
