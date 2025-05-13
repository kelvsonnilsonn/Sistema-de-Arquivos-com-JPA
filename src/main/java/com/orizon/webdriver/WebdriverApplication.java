package com.orizon.webdriver;

import com.orizon.webdriver.domain.service.factory.FileFactory;
import com.orizon.webdriver.domain.service.file.FileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class WebdriverApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebdriverApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(FileService fileService) {
		return args -> {

			System.out.println("Aplicação iniciada com sucesso!");
			fileService.createFile("Video");

		};
	}

}
