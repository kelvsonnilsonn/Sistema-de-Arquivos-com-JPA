package com.orizon.webdriver;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidPlanException;
import com.orizon.webdriver.domain.model.FileOperation;
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

import javax.sql.DataSource;
import java.sql.Connection;
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
	@Transactional
	public CommandLineRunner run(CommentService commentService, FileOperationService fileOperationService, FileService fileService,
								 InstitutionService institutionService, PlanService planService, SupportService supportService,
								 UserService userService, VersionHistoryService versionHistoryService) {
		return args -> {
			System.out.println("🚀 Sistema WebDriver Iniciado\n");

			AbstractUser user = userService.findOne(1L);
			System.out.println(user);

			System.out.println(fileService.findOne(5L));
			fileOperationService.listAll();
//			fileService.update(5L, "nome alterado", FileOperation.OperationType.EDIT);
//			fileService.create(user, "meu arquivo2", AbstractFile.FileType.VIDEO);


//			System.out.println(user);
//			System.out.println(user.getFiles());
		};
	}

//	@Bean
//	public CommandLineRunner testConnection(DataSource dataSource) {
//		return args -> {
//			try (Connection conn = dataSource.getConnection()) {
//				System.out.println("✅ Conexão com o banco estabelecida com sucesso!");
//			} catch (Exception e) {
//				System.err.println("❌ Falha na conexão com o banco:");
//			}
//		};
//	}
}