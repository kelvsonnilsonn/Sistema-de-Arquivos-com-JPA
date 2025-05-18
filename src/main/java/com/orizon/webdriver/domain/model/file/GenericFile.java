package com.orizon.webdriver.domain.model.file;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Setter;

@Setter
@Entity
@DiscriminatorValue("Generic")
public class GenericFile extends AbstractFile{

    protected GenericFile(){}

    public GenericFile(String fileMetaData) {
        super(fileMetaData);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
