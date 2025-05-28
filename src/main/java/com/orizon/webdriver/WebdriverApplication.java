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

			userService.create("kelvson", "kelvson@gmail.com", "12345678", true);
			fileService.create(userService.findOne(1L), "txt", AbstractFile.FileType.TEXT);
			userService.create("joao", "kelvson2@gmail.com", "12345678", false);
			fileService.shareFile(fileService.findOne(1L),
					userService.findOne(1L), userService.findOne(2L),
					Set.of(Permission.PermissionType.EDIT, Permission.PermissionType.DELETE));
		};
	}
}