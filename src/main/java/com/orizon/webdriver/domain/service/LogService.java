package com.orizon.webdriver.domain.service;

import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LogService {
    public void log(AFileInterface file, LogType type){
        System.out.println(file + "\n\nLOG FEITO EM: " + Instant.now() + "\n" + type.getDescription());
    }

    public enum LogType{
        CREATE("Criação"),
        DELETE("Deleção"),
        UPDATE("Edição");

        @Getter
        private String description;

        LogType(String description){
            this.description = description;
        }
    }
}
