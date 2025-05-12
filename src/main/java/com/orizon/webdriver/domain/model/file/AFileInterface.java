package com.orizon.webdriver.domain.model.file;

import com.orizon.webdriver.repository.FileRepository;

public interface AFileInterface {

    AFileInterface load();

    default void save(){
        FileRepository.save(this);
    }
    default void deleteFile(){
        FileRepository.delete(this);
    }
}
