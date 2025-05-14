package com.orizon.webdriver;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.institution.Address;
import com.orizon.webdriver.domain.model.institution.Institution;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.User;
import com.orizon.webdriver.domain.repository.InstitutionRepository;
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
	public CommandLineRunner run(UserService userService, InstitutionService institution, InstitutionRepository institutionRepository) {
		return args -> {

			System.out.println("Aplicação iniciada com sucesso!");
			AbstractUser user1 = new User("Kelvson", "kelvsonnilson01@gmail.com", "K1234");
			AbstractUser user2 = new User("Kelvson1", "kelvsonnilson01@gmail.com", "K1234");
			AbstractUser user3 = new User("Kelvson2", "kelvsonnilson01@gmail.com", "K1234");

			institutionRepository.getAllInstitutions();

			Institution institution1 = institution.createInstitution("MOJANG", "Minecraft");
			institution1.setAddress(new Address("Jaboatão dos Guararapes", "Pernambuco", "Barra de Jangada", "Rua Parnamirim"));
			institution1.setZipCode("54470200");
			institution1.getAddress().setComplement("Quadra 24b bloco C11");

			institutionRepository.getAllInstitutions();

			institution.deleteInstitution(0);

			institutionRepository.getAllInstitutions();

		};
	}

}
