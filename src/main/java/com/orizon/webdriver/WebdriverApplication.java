package com.orizon.webdriver;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidPlanException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.GenericFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.User;
import com.orizon.webdriver.domain.ports.service.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
@SpringBootApplication
public class WebdriverApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebdriverApplication.class, args);
	}

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}

	@Bean
	public CommandLineRunner run(CommentService commentService, FileOperationService fileOperationService, FileService fileService,
								 InstitutionService institutionService, PlanService planService, SupportService supportService,
								 UserService userService, VersionHistoryService versionHistoryService) {
		return args -> {
			System.out.println("🚀 Sistema WebDriver Iniciado\n");

			System.out.println(userService.findOne(1L));
			System.out.println(fileService.findOne(1L));

//			AbstractUser user = new User("Kelvson", "Kelvsonnilson01@gmail.com", "1234");
//			AbstractFile file = new GenericFile("meu arquivo1");
//			userService.save(user);
//			fileService.save(file);
//			commentService.create("Que alegria! Esse é o primeiro comentario que faço num arquivo meu!", user, file);
		};
	}
}