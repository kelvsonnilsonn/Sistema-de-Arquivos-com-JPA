package com.orizon.webdriver.domain.model.file;

import com.orizon.webdriver.domain.model.file.filedatas.FileInformations;

public class GenericFile extends AbstractFile{

    public GenericFile(FileInformations fileInformations) {
        super(fileInformations);
    }

    @Override
    public AFileInterface load() {
        return this;
    }
}
