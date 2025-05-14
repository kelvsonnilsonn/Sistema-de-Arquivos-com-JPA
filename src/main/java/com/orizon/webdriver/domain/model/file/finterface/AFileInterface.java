package com.orizon.webdriver.domain.model.file.finterface;

import com.orizon.webdriver.domain.repository.FileRepository;

public interface AFileInterface {

    AFileInterface load();

    void save(FileRepository fileRepository);

    void delete(FileRepository fileRepository);
}
