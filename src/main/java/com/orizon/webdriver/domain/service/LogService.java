package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LogService {
    public void log(AFileInterface file, LogType type){
//        System.out.println(file + "\n\nLOG FEITO EM: " + Instant.now() + "\n" + type.getDescription());
    }

    public void log(Comment comment){
        //System.out.println(comment + "\n\nLOG FEITO EM: " + Instant.now() + "\n" + LogType.COMMENT);
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
