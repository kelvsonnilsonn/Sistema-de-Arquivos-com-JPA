package com.orizon.webdriver;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.exceptions.InvalidPlanException;
import com.orizon.webdriver.domain.model.Institution;
import com.orizon.webdriver.domain.model.Plan;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.User;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.ports.repository.FileRepository;
import com.orizon.webdriver.domain.ports.repository.InstitutionRepository;
import com.orizon.webdriver.domain.ports.repository.PlanRepository;
import com.orizon.webdriver.domain.ports.service.*;
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
	public CommandLineRunner run(SupportService supportService, FileService fileService,
								 UserService userService, InstitutionService institutionService,
								 InstitutionRepository institutionRepository, FileRepository fileRepository,
								 Scanner scanner, PlanService planService) {
		return args -> {
			System.out.println("🚀 Sistema WebDriver Iniciado\n");

			// Dados temporários para demonstração
			AbstractUser currentUser = new User("admin", "admin@orizon.com", "senha123");
			Institution currentInstitution = null;

			while (true) {
				System.out.println("\n=== MENU PRINCIPAL ===");
				System.out.println("1. Gestão de Usuários");
				System.out.println("2. Gestão de Arquivos"); // adicionar opção para ler comentários de um arquivo
				System.out.println("3. Gestão de Instituições");
				System.out.println("4. Sistema de Suporte");
				System.out.println("5. Visualizar Dados Atuais");
				System.out.println("6. Sistema de planos");
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
					case 6 -> menuPlanos(planService, institutionService, scanner, currentInstitution);
					case 0 -> {
						System.out.println("\n👋 Encerrando sistema...");
						return;
					}
					default -> System.out.println("⚠️ Opção inválida!");
				}
			}
		};
	}

	private void menuPlanos(PlanService planService, InstitutionService institutionService,
							Scanner scanner, Institution currentInstitution) {
		while (true) {
			System.out.println("\n=== GESTÃO DE PLANOS ===");
			System.out.println("1. Criar novo plano");
			System.out.println("2. Associar plano à instituição");
			System.out.println("3. Atualizar duração do plano");
			System.out.println("4. Atualizar espaço do usuário");
			System.out.println("5. Visualizar plano atual");
			System.out.println("6. Listar todos os planos disponíveis");
			System.out.println("7. Remover plano");
			System.out.println("0. Voltar ao menu principal");
			System.out.print("Escolha uma opção: ");

			int opcao = scanner.nextInt();
			scanner.nextLine(); // Limpar buffer

			switch (opcao) {
				case 1 -> criarNovoPlano(planService, scanner);
				case 2 -> associarPlano(planService, institutionService, scanner, currentInstitution);
				case 3 -> atualizarDuracao(planService, scanner, currentInstitution);
				case 4 -> atualizarEspacoUsuario(planService, scanner, currentInstitution);
				case 5 -> visualizarPlanoAtual(planService, currentInstitution);
				case 6 -> listarPlanosDisponiveis(planService);
				case 7 -> removerPlano(planService, scanner);
				case 0 -> { return; }
				default -> System.out.println("⚠️ Opção inválida!");
			}
			return;
		}
	}

	private void removerPlano(PlanService planService, Scanner scanner) {
		List<Plan> planos = planService.getAllAvailablePlans();

		if (planos.isEmpty()) {
			System.out.println("⛔ Não há planos cadastrados para remover");
			return;
		}

		System.out.println("📋 Planos disponíveis para remoção:");
		planos.forEach(p -> System.out.println(p.getId() + " - " + p.getName()));

		System.out.print("Digite o ID do plano a ser removido: ");
		long id = scanner.nextLong();
		scanner.nextLine();

		try {
			Plan planoParaRemover = planos.stream()
					.filter(p -> p.getId() == id)
					.findFirst()
					.orElseThrow(() -> new InvalidPlanException("Plano não encontrado"));

			planService.deletePlan(id);
			System.out.println("✅ Plano '" + planoParaRemover.getName() + "' removido com sucesso");
		} catch (InvalidPlanException e) {
			System.out.println("⛔ " + e.getMessage());
		}
	}

	private void criarNovoPlano(PlanService planService, Scanner scanner) {
		System.out.print("Digite o nome do plano: ");
		String nome = scanner.nextLine();

		System.out.print("Digite o espaço de usuário (ex: 50GB): ");
		String espaco = scanner.nextLine();

		try {
			Plan novoPlano = planService.createPlan(nome, espaco);
			System.out.println("✅ Plano criado com sucesso:\n" + novoPlano);
		} catch (ENFieldException e) {
			System.out.println("⛔ Erro: Nome e espaço são obrigatórios!");
		}
	}

	private void associarPlano(PlanService planService, InstitutionService institutionService,
							   Scanner scanner, Institution instituicao) {
		if (instituicao == null) {
			System.out.println("⛔ Nenhuma instituição selecionada!");
			return;
		}

		System.out.println("Planos disponíveis:");
		planService.getAllAvailablePlans().forEach(System.out::println);

		System.out.print("Digite o ID do plano a ser associado: ");
		long idPlano = scanner.nextLong();
		scanner.nextLine();

		try {
			Plan plano = planService.getAllAvailablePlans().stream()
					.filter(p -> p.getId() == idPlano)
					.findFirst()
					.orElseThrow(InvalidPlanException::new);

			planService.assignPlanToInstitution(plano, instituicao);
			System.out.println("✅ Plano associado com sucesso à instituição " + instituicao.getName());
		} catch (InvalidPlanException e) {
			System.out.println("⛔ Plano não encontrado!");
		}
	}

	private void atualizarDuracao(PlanService planService, Scanner scanner, Institution instituicao) {
		if (instituicao == null || instituicao.getPlano() == null) {
			System.out.println("⛔ Nenhum plano associado à instituição!");
			return;
		}

		System.out.print("Digite a nova duração em dias: ");
		int dias = scanner.nextInt();
		scanner.nextLine();

		try {
			planService.updatePlanDuration(instituicao, Duration.ofDays(dias));
			System.out.println("✅ Duração atualizada para " + dias + " dias");
		} catch (InvalidPlanException e) {
			System.out.println("⛔ " + e.getMessage());
		}
	}

	private void atualizarEspacoUsuario(PlanService planService, Scanner scanner, Institution instituicao) {
		if (instituicao == null || instituicao.getPlano() == null) {
			System.out.println("⛔ Nenhum plano associado à instituição!");
			return;
		}

		System.out.print("Digite o novo espaço de usuário (ex: 100GB): ");
		String espaco = scanner.nextLine();

		try {
			planService.updatePlanUserSpace(instituicao, espaco);
			System.out.println("✅ Espaço do usuário atualizado para " + espaco);
		} catch (ENFieldException e) {
			System.out.println("⛔ Espaço não pode ser vazio!");
		}
	}

	private void visualizarPlanoAtual(PlanService planService, Institution instituicao) {
		if (instituicao == null) {
			System.out.println("⛔ Nenhuma instituição selecionada!");
			return;
		}

		try {
			Plan plano = planService.getInstitutionPlan(instituicao);
			if (plano != null) {
				System.out.println("📋 Plano atual:\n" + plano);
			} else {
				System.out.println("ℹ️ Nenhum plano associado à esta instituição");
			}
		} catch (ENFieldException e) {
			System.out.println("⛔ Instituição inválida!");
		}
	}

	private void listarPlanosDisponiveis(PlanService planService) {
		List<Plan> planos = planService.getAllAvailablePlans();

		if (planos.isEmpty()) {
			System.out.println("⛔ Nenhum plano cadastrado");
		} else {
			System.out.println("📋 Planos disponíveis:");
			planos.forEach(plan -> {
				System.out.println(plan);
				System.out.println("――――――――――――――――――");
			});
		}
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
		System.out.println("7. Visualizar comentários de um arquivo");
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
			case 7 -> {
				visualizarComentariosArquivo(fileRepository, scanner);
			}
		}
	}

	private void visualizarComentariosArquivo(FileRepository fileRepository, Scanner scanner) {
		List<FileOperations> arquivos = fileRepository.getAllFiles();

		if (arquivos.isEmpty()) {
			System.out.println("⛔ Nenhum arquivo cadastrado");
			return;
		}

		System.out.println("📋 Arquivos disponíveis:");
		arquivos.forEach(f -> System.out.println(((AbstractFile) f).getId() + " - " + ((AbstractFile) f).getFileName()));

		System.out.print("Digite o ID do arquivo para ver comentários: ");
		int idArquivo = scanner.nextInt();
		scanner.nextLine();

		AbstractFile arquivoSelecionado = (AbstractFile) arquivos.stream()
				.filter(f -> ((AbstractFile) f).getId() == idArquivo)
				.findFirst()
				.orElse(null);

		if (arquivoSelecionado != null) {
			System.out.println("\n💬 Comentários do arquivo " + arquivoSelecionado.getFileName() + ":");
			arquivoSelecionado.getCommentsInFile();
		} else {
			System.out.println("⛔ Arquivo não encontrado!");
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
					supportService.checkSupport(id); // Corrigido para passar o usuário
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