package com.orizon.webdriver.domain.model.file;

import com.orizon.webdriver.domain.model.file.filedatas.FileInformations;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;

public class GenericFile extends AbstractFile{

    public GenericFile(FileInformations fileInformations) {
        super(fileInformations);
    }

    @Override
    public AFileInterface load() {
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
