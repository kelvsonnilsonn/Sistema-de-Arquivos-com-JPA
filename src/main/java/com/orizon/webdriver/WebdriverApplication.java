package com.orizon.webdriver;

import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.User;
import com.orizon.webdriver.domain.model.user.uinterface.AUserInterface;
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
	public CommandLineRunner run(UserService userService) {
		return args -> {

			System.out.println("Aplicação iniciada com sucesso!");
			AbstractUser user1 = new User("Kelvson", "kelvsonnilson01@gmail.com", "K1234");
			AFileInterface file = userService.create("VIDEO", user1);
			AFileInterface file2 = userService.create("VIDEO", user1);
			System.out.println(user1.getUserFiles());
			userService.edit(file);
			System.out.println(user1.getUserFiles());
			userService.delete(file, user1);
			System.out.println(user1.getUserFiles());
		};
	}

}
