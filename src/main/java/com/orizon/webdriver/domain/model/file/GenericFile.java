package com.orizon.webdriver.domain.model.file;

import com.orizon.webdriver.domain.model.file.data.FileInformations;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.repository.FileRepository;
import lombok.Setter;

@Setter
public final class GenericFile extends AbstractFile{

    public GenericFile(FileInformations fileInformations) {
        super(fileInformations);
    }

    @Override
    public AFileInterface load() {
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
