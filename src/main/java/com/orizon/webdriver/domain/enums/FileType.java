package com.orizon.webdriver.domain.enums;

import com.orizon.webdriver.domain.exceptions.InvalidFileTypeException;
import lombok.Getter;

@Getter
public enum FileType {
    TEXT(".txt"),
    VIDEO(".wav"),
    PHOTO(".jpg");

    private final String description;

    public static FileType from(String type){
        try{
            return FileType.valueOf(type);
        } catch (IllegalArgumentException e){
            throw new InvalidFileTypeException();
        }
    }

    FileType(String description){
        this.description = description;
    }
}
