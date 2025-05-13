package com.orizon.webdriver.configuration;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.service.factory.FileFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Configuration
public class Beans {

    @Bean
    public List<AbstractFile.Permission> filePermission(){
        return new ArrayList<>(Arrays.asList(AbstractFile.Permission.values()));
    }

    @Bean
    public Scanner scan() { return new Scanner(System.in); }
}
