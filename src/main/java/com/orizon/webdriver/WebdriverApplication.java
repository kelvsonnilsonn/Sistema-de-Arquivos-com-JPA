package com.orizon.webdriver;

import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.User;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.service.FileService;
import com.orizon.webdriver.domain.service.SupportService;
import com.orizon.webdriver.infrastructure.repository.InstitutionRepository;
import com.orizon.webdriver.domain.service.InstitutionService;
import com.orizon.webdriver.domain.service.UserService;
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
	public CommandLineRunner run(SupportService supportService, FileService fileService, UserService userService, InstitutionService institution, InstitutionRepository institutionRepository) {
		return args -> {
			AbstractUser user = new User("kelvson", "kelvsonnilson01@gmail.com", "12345");
			supportService.addSupportRequest(user, "Urgente." ,"Preciso achar a cura de ser tão lindo.");
			System.out.println(user);
			supportService.getAllSupports();
			supportService.checkSupport(user, 0);
			System.out.println(user);
		};
	}
}
