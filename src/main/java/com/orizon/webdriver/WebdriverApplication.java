package com.orizon.webdriver;

import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.service.FileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebdriverApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebdriverApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(FileService fileService) {
		return args -> {

			AFileInterface file = fileService.createFile("Video");
			System.out.println("Aplicação iniciada com sucesso!");
			System.out.println(file);
			fileService.updateFile(file);
			System.out.println(file);
		};
	}

}
