package com.orizon.webdriver;

import com.orizon.webdriver.domain.model.Permission;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.ports.service.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;
import java.util.Set;

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
	@Transactional
	public CommandLineRunner run(CommentService commentService, FileOperationService fileOperationService, FileService fileService,
								 InstitutionService institutionService, PlanService planService, SupportService supportService,
								 UserService userService, VersionHistoryService versionHistoryService) {
		return args -> {
			System.out.println("🚀 Sistema WebDriver Iniciado\n");

//			institutionService.create("SUS", "Saúde Pública");
			institutionService.updateAddress(1L, "54470-200",
					"Rua das Flores", "123",
					"Centro", "Recife", "Pernambuco",
					"Brasil", "Recife");
		};
	}
}