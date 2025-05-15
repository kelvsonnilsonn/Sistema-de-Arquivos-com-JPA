package com.orizon.webdriver;

import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.User;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.ports.repository.FileRepository;
import com.orizon.webdriver.domain.service.FileService;
import com.orizon.webdriver.domain.service.SupportService;
import com.orizon.webdriver.domain.service.InstitutionService;
import com.orizon.webdriver.domain.service.UserService;
import com.orizon.webdriver.infrastructure.repository.InstitutionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	public CommandLineRunner run(SupportService supportService, FileService fileService,
								 UserService userService, InstitutionService institutionService,
								 InstitutionRepository institutionRepository, FileRepository fileRepository,
								 Scanner scanner) {
		return args -> {
			System.out.println("🚀 Sistema WebDriver Iniciado\n");

			// Dados temporários para demonstração
			AbstractUser currentUser = new User("admin", "admin@orizon.com", "senha123");
			Institution currentInstitution = null;

			while (true) {
				System.out.println("\n=== MENU PRINCIPAL ===");
				System.out.println("1. Gestão de Usuários");
				System.out.println("2. Gestão de Arquivos");
				System.out.println("3. Gestão de Instituições");
				System.out.println("4. Sistema de Suporte");
				System.out.println("5. Visualizar Dados Atuais");
				System.out.println("0. Sair");
				System.out.print("Escolha uma opção: ");

				int opcao = scanner.nextInt();
				scanner.nextLine(); // Limpar buffer

				switch (opcao) {
					case 1 -> currentUser = menuUsuarios(userService, scanner, currentUser);
					case 2 -> menuArquivos(fileService, userService, scanner, currentUser, fileRepository);
					case 3 -> currentInstitution = menuInstituicoes(institutionService, institutionRepository, scanner, currentUser, currentInstitution);
					case 4 -> menuSuporte(supportService, scanner, currentUser);
					case 5 -> visualizarDadosAtuais(currentUser, currentInstitution);
					case 0 -> {
						System.out.println("\n👋 Encerrando sistema...");
						return;
					}
					default -> System.out.println("⚠️ Opção inválida!");
				}
			}
		};
	}

	private void visualizarDadosAtuais(AbstractUser user, Institution institution) {
		System.out.println("\n=== DADOS ATUAIS ===");
		System.out.println(user);
	}

	private AbstractUser menuUsuarios(UserService userService, Scanner scanner, AbstractUser currentUser) {
		System.out.println("\n=== GESTÃO DE USUÁRIOS ===");
		System.out.println("1. Criar novo usuário");
		System.out.println("2. Trocar usuário atual");
		System.out.println("0. Voltar");
		System.out.print("Escolha: ");

		int opcao = scanner.nextInt();
		scanner.nextLine();

		switch (opcao) {
			case 1 -> {
				System.out.print("Login: ");
				String login = scanner.nextLine();
				System.out.print("Email: ");
				String email = scanner.nextLine();
				System.out.print("Senha: ");
				String senha = scanner.nextLine();
				AbstractUser novoUser = new User(login, email, senha);
				System.out.println("✅ Usuário criado: " + novoUser.getUserLogin());
				return novoUser;
			}
			case 2 -> {
				System.out.print("Digite o login do usuário: ");
				String login = scanner.nextLine();
				System.out.print("Digite a senha: ");
				String senha = scanner.nextLine();
				// Simulação de autenticação
				if (login.equals(currentUser.getUserLogin())){
					System.out.println("✅ Usuário autenticado");
				} else {
					System.out.println("⚠️ Usuário não encontrado, mantendo o atual");
				}
			}
		}
		return currentUser;
	}

	private void menuArquivos(FileService fileService, UserService userService, Scanner scanner, AbstractUser user, FileRepository fileRepository) {
		System.out.println("\n=== GESTÃO DE ARQUIVOS ===");
		System.out.println("1. Criar arquivo");
		System.out.println("2. Deletar arquivo");
		System.out.println("3. Adicionar comentário");
		System.out.println("4. Buscar arquivo por ID");
		System.out.println("5. Buscar arquivo por nome");
		System.out.println("6. Listar meus arquivos");
		System.out.println("0. Voltar");
		System.out.print("Escolha: ");

		int opcao = scanner.nextInt();
		scanner.nextLine();

		switch (opcao) {
			case 1 -> {
				System.out.print("Digite o tipo (VIDEO/GENERIC): ");
				String tipo = scanner.nextLine();
				try {
					FileOperations file = userService.create(tipo, user);
					System.out.println("✅ Arquivo criado: " + ((AbstractFile) file).getFileName());
				} catch (Exception e) {
					System.out.println("❌ Erro: " + e.getMessage());
				}
			}
			case 2 -> {
				System.out.print("Digite o ID do arquivo: ");
				int id = scanner.nextInt();
				scanner.nextLine();
				user.getUserFiles().stream()
						.filter(f -> ((AbstractFile) f).getId() == id)
						.findFirst()
						.ifPresentOrElse(
								file -> {
									userService.delete(file, user);
									System.out.println("✅ Arquivo deletado");
								},
								() -> System.out.println("❌ Arquivo não encontrado")
						);
			}
			case 3 -> {
				System.out.print("Digite o ID do arquivo: ");
				int id = scanner.nextInt();
				scanner.nextLine();
				System.out.print("Digite o comentário: ");
				String comentario = scanner.nextLine();

				user.getUserFiles().stream()
						.filter(f -> ((AbstractFile) f).getId() == id)
						.findFirst()
						.ifPresentOrElse(
								file -> {
									userService.comment(file, comentario);
									System.out.println("✅ Comentário adicionado");
								},
								() -> System.out.println("❌ Arquivo não encontrado")
						);
			}
			case 4 -> {
				System.out.print("Digite o ID do arquivo: ");
				int id = scanner.nextInt();
				scanner.nextLine();

				// Busca no repositório de arquivos
				Optional<FileOperations> arquivo = fileRepository.search(id);

				if (arquivo.isPresent()) {
					AbstractFile file = (AbstractFile) arquivo.get();
					System.out.println("🔍 Arquivo encontrado:");
					System.out.println(file); // Usa o toString() do AbstractFile
				} else {
					System.out.println("❌ Arquivo não encontrado");
				}
			}
			case 5 -> {
				System.out.print("Digite o nome do arquivo: ");
				String nome = scanner.nextLine();
				List<FileOperations> arquivos = fileService.search(nome);
				if (arquivos.isEmpty()) {
					System.out.println("❌ Nenhum arquivo encontrado");
				} else {
					System.out.println("🔍 Arquivos encontrados:");
					arquivos.forEach(file ->
							System.out.println("- " + ((AbstractFile) file).getFileName())
					);
				}
			}
			case 6 -> {
				System.out.println("\n📂 SEUS ARQUIVOS:");
				user.getUserFiles().forEach(file ->
						System.out.println("- " + ((AbstractFile) file).getFileName() +
								" (ID: " + ((AbstractFile) file).getId() + ")")
				);
			}
		}
	}

	private Institution menuInstituicoes(InstitutionService institutionService, InstitutionRepository institutionRepository,
										 Scanner scanner, AbstractUser user, Institution currentInstitution) {
		System.out.println("\n=== GESTÃO DE INSTITUIÇÕES ===");
		System.out.println("1. Criar instituição");
		System.out.println("2. Vincular usuário");
		System.out.println("3. Listar instituições");
		System.out.println("4. Definir instituição atual");
		System.out.println("0. Voltar");
		System.out.print("Escolha: ");

		int opcao = scanner.nextInt();
		scanner.nextLine();

		switch (opcao) {
			case 1 -> {
				System.out.print("Nome da instituição: ");
				String nome = scanner.nextLine();
				System.out.print("Causa social: ");
				String causa = scanner.nextLine();
				try {
					Institution instituicao = institutionService.createInstitution(nome, causa);
					System.out.println("✅ Instituição criada: " + instituicao.getName());
					return instituicao;
				} catch (Exception e) {
					System.out.println("❌ Erro: " + e.getMessage());
				}
			}
			case 2 -> {
				if (currentInstitution == null) {
					System.out.println("❌ Nenhuma instituição selecionada");
					break;
				}
				try {
					institutionService.addInstitutionUser(currentInstitution, user);
					System.out.println("✅ Usuário vinculado à instituição");
				} catch (Exception e) {
					System.out.println("❌ Erro: " + e.getMessage());
				}
			}
			case 3 -> {
				System.out.println("\n🏢 INSTITUIÇÕES:");
				institutionRepository.getAllInstitutions();
			}
			case 4 -> {
				System.out.print("Digite o ID da instituição: ");
				long id = scanner.nextLong();
				scanner.nextLine();
				Institution instituicao = institutionRepository.institutionSearch(id);
				if (instituicao != null) {
					System.out.println("✅ Instituição selecionada: " + instituicao.getName());
					return instituicao;
				} else {
					System.out.println("❌ Instituição não encontrada");
				}
			}
		}
		return currentInstitution;
	}

	private void menuSuporte(SupportService supportService, Scanner scanner, AbstractUser user) {
		System.out.println("\n=== SISTEMA DE SUPORTE ===");
		System.out.println("1. Abrir ticket");
		System.out.println("2. Resolver ticket");
		System.out.println("3. Listar meus tickets");
		System.out.println("4. Listar todos tickets");
		System.out.println("0. Voltar");
		System.out.print("Escolha: ");

		int opcao = scanner.nextInt();
		scanner.nextLine();

		switch (opcao) {
			case 1 -> {
				System.out.print("Título: ");
				String titulo = scanner.nextLine();
				System.out.print("Descrição: ");
				String descricao = scanner.nextLine();
				try {
					supportService.addSupportRequest(user, titulo, descricao);
					System.out.println("✅ Ticket criado com sucesso!");
				} catch (Exception e) {
					System.out.println("❌ Erro: " + e.getMessage());
				}
			}
			case 2 -> {
				System.out.print("Digite o ID do ticket: ");
				long id = scanner.nextLong();
				scanner.nextLine();
				try {
					supportService.checkSupport(user, id); // Corrigido para passar o usuário
					System.out.println("✅ Ticket marcado como resolvido");
				} catch (Exception e) {
					System.out.println("❌ Erro: " + e.getMessage());
				}
			}
			case 3 -> {
				System.out.println("\n🆘 SEUS TICKETS:");
				user.getSupportRequests().forEach(s ->
						System.out.println("- ID: " + s.getId() +
								" | Título: " + s.getTitle() +
								" | Status: " + (s.isResolved() ? "✅ Resolvido" : "🟡 Pendente"))
				);
			}
			case 4 -> {
				System.out.println("\n🆘 TODOS OS TICKETS:");
				supportService.getAllSupports();
			}
		}
	}
}