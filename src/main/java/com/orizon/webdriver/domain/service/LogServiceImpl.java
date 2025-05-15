package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.ports.service.LogService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LogServiceImpl implements LogService {
    @Override
    public void log(FileOperations file, LogType type){
        System.out.println(file + "\nLOG FEITO EM: " + Instant.now() + "\n" + type.getDescription());
    }

    @Override
    public void log(Comment comment){
        System.out.println(comment + "\nLOG FEITO EM: " + Instant.now() + "\n" + LogType.COMMENT);
    }

    @Getter
    public enum LogType{
        CREATE("Criação"),
        DELETE("Deleção"),
        UPDATE("Edição"),
        COMMENT("Comentário");

        private final String description;

        LogType(String description){
            this.description = description;
        }
    }
}
