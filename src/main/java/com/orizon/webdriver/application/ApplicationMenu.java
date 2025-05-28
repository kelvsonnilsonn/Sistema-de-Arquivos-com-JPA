package com.orizon.webdriver.application;

import com.orizon.webdriver.domain.model.*;
import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.file.GenericFile;
import com.orizon.webdriver.domain.model.file.VideoFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.Administrator;
import com.orizon.webdriver.domain.ports.service.*;
import com.orizon.webdriver.domain.valueobjects.UserAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@RequiredArgsConstructor
public class ApplicationMenu {

    private final Scanner scanner;
    private AbstractUser actualUser;

    // Services
    private final UserService userService;
    private final FileService fileService;
    private final InstitutionService institutionService;
    private final PlanService planService;
    private final SupportService supportService;
    private final CommentService commentService;
    private final FileOperationService fileOperationService;
    private final VersionHistoryService versioningHistoryService;

    public void start() throws SQLException {
        boolean authenticated = false;

        while (!authenticated) {
            System.out.println("\n=== BEM-VINDO ===");
            System.out.println("1. Login");
            System.out.println("2. Registrar novo usuário");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int initialChoice = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (initialChoice) {
                case 1 -> {
                    System.out.println("\n=== LOGIN ===");
                    System.out.print("Login: ");
                    String login = scanner.nextLine();
                    System.out.print("Senha: ");
                    String password = scanner.nextLine();

                    if (userService.login(login, password)) {
                        actualUser = userService.getCurrentUser();
                        System.out.println("Login bem-sucedido!");
                        authenticated = true;
                        showMainMenu();
                    } else {
                        System.out.println("Login falhou!");
                    }
                }
                case 2 -> registerNewUser();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void registerNewUser() throws SQLException {
        System.out.println("\n=== NOVO CADASTRO ===");
        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        // Por padrão, novos usuários não são administradores
        userService.create(login, email, password, false);

        System.out.println("Usuário registrado com sucesso! Faça login para continuar.");
    }

    private void showMainMenu() throws SQLException {
        boolean running = true;

        while (running) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("Usuário: " + actualUser.getUsername());
            System.out.println("1. Gerenciar Usuários");
            System.out.println("2. Gerenciar Arquivos");
            System.out.println("3. Gerenciar Instituições");
            System.out.println("4. Gerenciar Planos");
            System.out.println("5. Gerenciar Suporte");
            System.out.println("6. Gerenciar Comentários");
            System.out.println("7. Gerenciar Operações de Arquivo");
            System.out.println("8. Gerenciar Histórico de Versões");
            System.out.println("9. Logout");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (choice) {
                case 1 -> userMenu();
                case 2 -> fileMenu();
                case 3 -> institutionMenu();
                case 4 -> planMenu();
                case 5 -> supportMenu();
                case 6 -> commentMenu();
                case 7 -> fileOperationMenu();
                case 8 -> versioningHistoryMenu();
                case 9 -> {
                    userService.logout();
                    running = false;
                    start();
                }
                case 0 -> running = false;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void userMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== MENU DE USUÁRIOS ===");
            System.out.println("1. Buscar usuário por ID");
            System.out.println("2. Listar todos os usuários");
            System.out.println("3. Atualizar usuário");
            System.out.println("4. Deletar usuário");
            System.out.println("5. Associar instituição a usuário");
            System.out.println("6. Promover usuário a admin");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("ID do usuário: ");
                    Long id = scanner.nextLong();
                    AbstractUser user = userService.findById(id);
                    System.out.println(user);
                }
                case 2 -> {
                    System.out.println("Lista de usuários:");
                    userService.findAll();
                }
                case 3 -> {
                    System.out.println("Digite o ID de quem vai editar: ");
                    Long id = scanner.nextLong();
                    AbstractUser userAtual = userService.findById(id);
                    System.out.println("Escolha o que quer editar [1 - nome/ 2 - email/ 3 - senha]: ");
                    int op = scanner.nextInt();
                    scanner.nextLine();
                    switch (op) {
                        case 1 -> {
                            System.out.println("Novo nome: ");
                            String nome = scanner.nextLine();
                            userAtual.setUsername(nome);
                        }
                        case 2 -> {
                            System.out.println("Novo email: ");
                            String email = scanner.nextLine();
                            userAtual.setUserAccess(new UserAccess(email, userAtual.getUserAccess().getPassword()));
                        }
                        case 3 -> {
                            System.out.println("Nova senha: ");
                            String senha = scanner.nextLine();
                            userAtual.setUserAccess(new UserAccess(userAtual.getUserAccess().getEmail(), senha));
                        }
                    }
                    userService.update(userAtual);
                }
                case 4 -> {
                    System.out.print("ID do usuário a deletar: ");
                    Long id = scanner.nextLong();
                    userService.delete(id);
                    System.out.println("Usuário deletado com sucesso!");
                }
                case 5 -> {
                    System.out.print("ID do usuário: ");
                    Long userId = scanner.nextLong();
                    System.out.print("ID da instituição: ");
                    Long institutionId = scanner.nextLong();
                    AbstractUser user = userService.findById(userId);
                    Institution institution = institutionService.findById(institutionId);
                    user.setInstitution(institution);
                    userService.update(user);
                    System.out.println("Instituição associada com sucesso!");
                }
                case 6 -> {
                    System.out.print("ID do usuário a promover: ");
                    Long userId = scanner.nextLong();
                    userService.promoteToAdmin(userId);
                    System.out.println("Usuário promovido a admin com sucesso!");
                }
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void fileMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== MENU DE ARQUIVOS ===");
            System.out.println("1. Criar arquivo");
            System.out.println("2. Buscar meus arquivos");
            System.out.println("3. Listar arquivos visíveis");
            System.out.println("4. Atualizar arquivo");
            System.out.println("5. Deletar arquivo");
            System.out.println("6. Compartilhar arquivo");
            System.out.println("7. Ver histórico de operações");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {  // Criar arquivo - mantido igual
                    AbstractFile file;
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Tipo de arquivo [1 - video/ 2 - foto/ 3 - text]: ");
                    int op = scanner.nextInt();
                    scanner.nextLine();

                    if (op == 1) {
                        System.out.println("Duração em segundos: ");
                        Duration duration = Duration.ofSeconds(scanner.nextLong());
                        file = new VideoFile(nome, duration);
                    } else {
                        file = new GenericFile(nome);
                    }
                    fileService.create(file, actualUser);  // Usa o usuário atual
                    for(Permission.PermissionType permission : Permission.PermissionType.values()){
                        fileService.addPermission(fileService.findByName(file.getName()).getId(), actualUser.getId(), permission);
                    }
                    System.out.println("Arquivo criado com sucesso!");
                }

                case 2 -> {  // Buscar meus arquivos
                    System.out.print("Nome do arquivo: ");
                    String fileName = scanner.nextLine();

                    AbstractFile fileOpt = fileService.findByNameAndOwnerId(fileName, actualUser.getId());

                    if (fileOpt != null && fileService.hasPermission(actualUser, fileOpt, Permission.PermissionType.VIEW)) {
                        showDetails(fileOpt);
                    } else {
                        System.out.println("Arquivo não encontrado ou sem permissão");
                    }
                }

                case 3 -> {  // Listar arquivos visíveis
                    System.out.println("\n=== ARQUIVOS DISPONÍVEIS ===");
                    Set<AbstractFile> visibleFiles = fileService.findVisibleFiles(actualUser);
                    visibleFiles.forEach(file ->
                            System.out.println("- " + file.getName() +
                                    " (" + file.getClass().getSimpleName() + ")" +
                                    " - Dono: " + file.getUser().getUsername()));
                }

                case 4 -> {  // Atualizar arquivo
                    System.out.println("\nSeus arquivos:");
                    Set<AbstractFile> myFiles = fileService.findByUserId(actualUser.getId());
                    myFiles.forEach(f -> System.out.println("- " + f.getName()));

                    System.out.print("\nNome do arquivo a editar: ");
                    String fileName = scanner.nextLine();

                    AbstractFile fileOpt = fileService.findByNameAndOwnerId(fileName, actualUser.getId());

                    if (fileOpt != null) {
                        if (fileService.hasPermission(actualUser, fileOpt, Permission.PermissionType.EDIT)) {
                            System.out.print("Novo nome: ");
                            String newName = scanner.nextLine();

                            System.out.print("Mensagem do commit (opcional): ");
                            String message = scanner.nextLine();
                            if (message.isBlank()) {
                                message = "Atualização sem mensagem";
                            }

                            VersioningHistory version = new VersioningHistory(actualUser, fileOpt, message);
                            fileOpt.setName(newName);
                            fileService.update(fileOpt, version, FileOperation.OperationType.EDIT);
                            System.out.println("Arquivo atualizado com sucesso!");
                        } else {
                            System.out.println("Você não tem permissão para editar este arquivo");
                        }
                    } else {
                        System.out.println("Arquivo não encontrado");
                    }
                }

                case 5 -> {  // Deletar arquivo
                    if (actualUser instanceof Administrator) {
                        // Fluxo para administrador
                        System.out.println("\n=== MODO ADMINISTRADOR ===");
                        System.out.println("Todos os arquivos:");

                        Set<AbstractFile> allFiles = fileService.findVisibleFiles(actualUser);
                        allFiles.forEach(f ->
                                System.out.println("- " + f.getName() + " (Dono: " + f.getUser().getUsername() + ")")
                        );

                        System.out.print("\nNome do arquivo a deletar: ");
                        String fileName = scanner.nextLine();

                        AbstractFile fileOpt = fileService.findByName(fileName);

                        if (fileOpt != null) {
                            System.out.println("Você está prestes a deletar:");
                            System.out.println("Nome: " + fileOpt.getName());
                            System.out.println("Dono: " + fileOpt.getUser().getUsername());

                            System.out.print("Confirmar deleção? (S/N): ");
                            String confirmation = scanner.nextLine();

                            if (confirmation.equalsIgnoreCase("S")) {
                                fileService.delete(fileOpt.getId());
                                System.out.println("Arquivo deletado com sucesso!");
                            } else {
                                System.out.println("Deleção cancelada");
                            }
                        } else {
                            System.out.println("Arquivo não encontrado");
                        }
                    } else {
                        // Fluxo para usuário normal
                        System.out.println("\nSeus arquivos:");
                        Set<AbstractFile> myFiles = fileService.findByUserId(actualUser.getId());
                        myFiles.forEach(f -> System.out.println("- " + f.getName()));

                        System.out.print("\nNome do arquivo a deletar: ");
                        String fileName = scanner.nextLine();

                        AbstractFile fileOpt = fileService.findByNameAndOwnerId(fileName, actualUser.getId());

                        if (fileOpt != null) {
                            if (fileService.hasPermission(actualUser, fileOpt, Permission.PermissionType.DELETE)) {
                                fileService.delete(fileOpt.getId());
                                System.out.println("Arquivo deletado com sucesso!");
                            } else {
                                System.out.println("Você não tem permissão para deletar este arquivo");
                            }
                        } else {
                            System.out.println("Arquivo não encontrado");
                        }
                    }
                }

                case 6 -> {  // Compartilhar arquivo
                    System.out.println("\nSeus arquivos:");
                    Set<AbstractFile> myFiles = fileService.findByUserId(actualUser.getId());
                    myFiles.forEach(f -> System.out.println("- " + f.getName()));

                    System.out.print("\nNome do arquivo a compartilhar: ");
                    String fileName = scanner.nextLine();

                    AbstractFile fileOpt = fileService.findByNameAndOwnerId(fileName, actualUser.getId());

                    if (fileOpt != null) {
                        if (fileService.hasPermission(actualUser, fileOpt, Permission.PermissionType.SHARE)) {
                            System.out.print("Login do usuário para compartilhar: ");
                            String receiverLogin = scanner.nextLine();

                            AbstractUser receiverOpt = userService.findByUsername(receiverLogin);

                            if (receiverOpt != null) {
                                // Seleção de permissões
                                System.out.println("\nSelecione as permissões (separar números por vírgula):");
                                System.out.println("1. Visualizar");
                                System.out.println("2. Editar");
                                System.out.println("3. Deletar");
                                System.out.println("4. Compartilhar");
                                System.out.println("5. Comentar");
                                System.out.print("Permissões: ");

                                String[] permInput = scanner.nextLine().split(",");
                                Set<Permission.PermissionType> permissions = new HashSet<>();

                                for (String input : permInput) {
                                    switch (input.trim()) {
                                        case "1" -> permissions.add(Permission.PermissionType.VIEW);
                                        case "2" -> permissions.add(Permission.PermissionType.EDIT);
                                        case "3" -> permissions.add(Permission.PermissionType.DELETE);
                                        case "4" -> permissions.add(Permission.PermissionType.SHARE);
                                        case "5" -> permissions.add(Permission.PermissionType.COMMENT);
                                    }
                                }

                                // Compartilha com as permissões selecionadas
                                fileService.shareFile(fileOpt, actualUser, receiverOpt, permissions);
                                System.out.println("Arquivo compartilhado com sucesso!");
                            } else {
                                System.out.println("Usuário não encontrado");
                            }
                        } else {
                            System.out.println("Você não tem permissão para compartilhar este arquivo");
                        }
                    } else {
                        System.out.println("Arquivo não encontrado");
                    }
                }

                case 7 -> {  // Ver histórico de operações
                    System.out.println("\n=== HISTÓRICO DE OPERAÇÕES ===");
                    Set<FileOperation> operations = fileOperationService.findByUserId(actualUser.getId());

                    if (operations.isEmpty()) {
                        System.out.println("Nenhuma operação encontrada");
                    } else {
                        operations.forEach(op ->
                                System.out.println("- " + op.getOperationType() +
                                        " em " + op.getFile().getName() +
                                        " (" + op.getOperationDate() + ")"));
                    }
                }

                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void institutionMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== MENU DE INSTITUIÇÕES ===");
            System.out.println("1. Criar instituição");
            System.out.println("2. Buscar instituição por ID");
            System.out.println("3. Listar todas as instituições");
            System.out.println("4. Atualizar instituição");
            System.out.println("5. Deletar instituição");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String name = scanner.nextLine();
                    System.out.print("Causa social: ");
                    String socialCause = scanner.nextLine();
                    institutionService.create(name, socialCause);
                }
                case 2 -> {
                    System.out.print("ID da instituição: ");
                    Long id = scanner.nextLong();
                    Institution institution = institutionService.findById(id);
                    System.out.println(institution);
                }
                case 3 -> {
                    System.out.println("Lista de instituições: ");
                    institutionService.findAll();
                }
                case 4 -> {
                    System.out.print("Digite o ID da instituição a editar: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Digite a opção do que quer editar [1 - nome/ 2 - causa social/ 3 - endereço]: ");
                    int op = scanner.nextInt();
                    scanner.nextLine();
                    switch (op) {
                        case 1 -> {
                            System.out.print("Digite o novo nome da instituição: ");
                            String novo = scanner.nextLine();
                            institutionService.updateName(id, novo);
                        }
                        case 2 -> {
                            System.out.print("Digite a nova causa social da instituição: ");
                            String novo = scanner.nextLine();
                            institutionService.updateSocialCause(id, novo);
                        }
                        case 3 -> {
                            System.out.println("\n--- Editar Endereço ---");

                            // CEP
                            System.out.print("CEP (apenas números): ");
                            String cep = scanner.nextLine();// Assumindo que ZipCode valida o formato

                            // Demais campos
                            System.out.print("Logradouro (Rua/Av.): ");
                            String street = scanner.nextLine();

                            System.out.print("Número: ");
                            String number = scanner.nextLine();

                            System.out.print("Bairro: ");
                            String neighborhood = scanner.nextLine();

                            System.out.print("Cidade: ");
                            String city = scanner.nextLine();

                            System.out.print("Estado (sigla): ");
                            String state = scanner.nextLine();

                            System.out.print("País: ");
                            String country = scanner.nextLine();

                            System.out.print("Complemento (opcional): ");
                            String complement = scanner.nextLine();

                            // Chama o serviço
                            institutionService.updateAddress(
                                    id, cep, street, number, neighborhood,
                                    city, state, country, complement
                            );

                            System.out.println("Endereço atualizado com sucesso!");
                        }
                        default -> System.out.println("Opção inválida!");
                    }
                }
                case 5 -> {
                    System.out.print("ID da instituição a deletar: ");
                    Long id = scanner.nextLong();
                    institutionService.delete(id);
                    System.out.println("Instituição deletada com sucesso!");
                }
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void planMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== MENU DE PLANOS ===");
            System.out.println("1. Criar plano");
            System.out.println("2. Buscar plano por ID");
            System.out.println("3. Listar todos os planos");
            System.out.println("4. Atualizar plano");
            System.out.println("5. Deletar plano");
            System.out.println("6. Associar plano a instituição");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String name = scanner.nextLine();
                    System.out.print("Espaço por usuário (MB): ");
                    int userSpace = scanner.nextInt();
                    planService.create(name, userSpace);
                }
                case 2 -> {
                    System.out.print("ID do plano: ");
                    Long id = scanner.nextLong();
                    Plan plan = planService.findById(id);
                    System.out.println(plan);
                }
                case 3 -> {
                    planService.findAll();
                }
                case 4 -> {
                    System.out.print("Id do plano: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("O que deseja editar [1 - nome/ 2 - espaço]: ");
                    int op = scanner.nextInt();
                    scanner.nextLine();
                    if (op == 1) {
                        System.out.print("Digite o novo nome: ");
                        String novo = scanner.nextLine();
                        planService.updateName(id, novo);
                    } else {
                        System.out.print("Digite o novo espaço: ");
                        int novo = scanner.nextInt();
                        planService.updateUserSpace(id, novo);
                    }
                }
                case 5 -> {
                    System.out.print("ID do plano a deletar: ");
                    Long id = scanner.nextLong();
                    planService.delete(id);
                    System.out.println("Plano deletado com sucesso!");
                }
                case 6 -> {
                    System.out.print("ID do plano: ");
                    Long planId = scanner.nextLong();
                    System.out.print("ID da instituição: ");
                    Long institutionId = scanner.nextLong();
                    planService.assignToInstitution(planId, institutionId);
                    System.out.println("Plano associado com sucesso!");
                }
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void supportMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== MENU DE SUPORTE ===");
            System.out.println("1. Criar ticket de suporte");
            System.out.println("2. Buscar ticket por ID");
            System.out.println("3. Listar todos os tickets");
            System.out.println("4. Atribuir admin a ticket");
            System.out.println("5. Deletar ticket");
            System.out.println("6. Resolver ticket");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("ID do autor: ");
                    Long authorId = scanner.nextLong();
                    System.out.print("ID do arquivo: ");
                    Long fileId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Título: ");
                    String title = scanner.nextLine();
                    System.out.print("Mensagem: ");
                    String body = scanner.nextLine();

                    AbstractUser author = userService.findById(authorId);
                    AbstractFile file = fileService.findById(fileId);
                    supportService.create(author, file, title, body);
                    System.out.println("Ticket criado com sucesso!");
                }
                case 2 -> {
                    System.out.print("ID do ticket: ");
                    Long id = scanner.nextLong();
                    Support support = supportService.findById(id);
                    System.out.println(support);
                }
                case 3 -> {
                    supportService.findAll();
                }
                case 4 -> {
                    System.out.print("ID do ticket: ");
                    Long supportId = scanner.nextLong();
                    System.out.print("ID do admin: ");
                    Long adminId = scanner.nextLong();
                    AbstractUser admin = userService.findById(adminId);
                    if (admin instanceof Administrator) {
                        supportService.assignAdminToSupport(supportId, (Administrator) admin);
                        System.out.println("Admin atribuído com sucesso!");
                    } else {
                        System.out.println("O usuário não é um administrador!");
                    }
                }
                case 5 -> {
                    System.out.print("ID do ticket a deletar: ");
                    Long id = scanner.nextLong();
                    supportService.delete(id);
                    System.out.println("Ticket deletado com sucesso!");
                }

                case 6 -> {
                    System.out.print("ID do ticket a resolver: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("ID do administrador a resolver: ");
                    Long adminId = scanner.nextLong();
                    scanner.nextLine();
                    AbstractUser admin = userService.findById(adminId);
                    if (!(admin instanceof Administrator)) {
                        System.out.println("Não é administrador");
                        return;
                    }
                    supportService.resolveSupport(id, (Administrator) admin);
                }
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void commentMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== MENU DE COMENTÁRIOS ===");
            System.out.println("1. Criar comentário");
            System.out.println("2. Buscar comentário por ID");
            System.out.println("3. Listar todos os comentários");
            System.out.println("4. Listar comentários por arquivo");
            System.out.println("5. Listar comentários por autor");
            System.out.println("6. Deletar comentário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Digite o conteúdo do comentário: ");
                    String body = scanner.nextLine();
                    System.out.print("Digite o Id do autor: ");
                    Long authorId = scanner.nextLong();
                    AbstractUser author = userService.findById(authorId);
                    scanner.nextLine();
                    System.out.print("Digite o Id do arquivo: ");
                    Long fileId = scanner.nextLong();
                    AbstractFile file = fileService.findById(fileId);
                    scanner.nextLine();
                    Comment comment = new Comment(body);
                    author.addComment(comment);
                    file.addComment(comment);
                    commentService.create(comment);
                }
                case 2 -> {
                    System.out.print("ID do comentário: ");
                    Long id = scanner.nextLong();
                    Comment comment = commentService.findById(id);
                    System.out.println(comment);
                }
                case 3 -> {
                    commentService.findAll();
                }
                case 4 -> {
                    System.out.print("ID do arquivo: ");
                    Long fileId = scanner.nextLong();
                    Set<Comment> comments = commentService.findByFileId(fileId);
                    comments.forEach(System.out::println);
                }
                case 5 -> {
                    System.out.print("ID do autor: ");
                    Long authorId = scanner.nextLong();
                    Set<Comment> comments = commentService.findByAuthorId(authorId);
                    comments.forEach(System.out::println);
                }
                case 6 -> {
                    System.out.print("ID do comentário a deletar: ");
                    Long id = scanner.nextLong();
                    commentService.delete(id);
                    System.out.println("Comentário deletado com sucesso!");
                }
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void fileOperationMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== MENU DE OPERAÇÕES DE ARQUIVO ===");
            System.out.println("1. Buscar operação por ID");
            System.out.println("2. Listar todas as operações");
            System.out.println("3. Listar operações por arquivo");
            System.out.println("4. Listar operações por autor");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("ID da operação: ");
                    Long id = scanner.nextLong();
                    FileOperation operation = fileOperationService.findById(id);
                    System.out.println(operation);
                }
                case 2 -> {
                    fileOperationService.findAll();
                }
                case 3 -> {
                    System.out.print("ID do arquivo: ");
                    Long fileId = scanner.nextLong();
                    Set<FileOperation> operations = fileOperationService.findByFileId(fileId);
                    operations.forEach(System.out::println);
                }
                case 4 -> {
                    System.out.print("ID do autor: ");
                    Long authorId = scanner.nextLong();
                    Set<FileOperation> operations = fileOperationService.findByUserId(authorId);
                    operations.forEach(System.out::println);
                }
                case 0 -> back = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void versioningHistoryMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n=== MENU DE HISTÓRICO DE VERSÕES ===");
            System.out.println("1. Buscar versão por ID");
            System.out.println("2. Listar todas as versões");
            System.out.println("3. Listar versões por arquivo");
            System.out.println("4. Atualizar mensagem de commit");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("ID da versão: ");
                    Long id = scanner.nextLong();
                    VersioningHistory version = versioningHistoryService.findById(id);
                    System.out.println(version);
                }
                case 2 -> {
                    versioningHistoryService.findAll();
                }
                case 3 -> {
                    System.out.print("ID do arquivo: ");
                    Long fileId = scanner.nextLong();
                    AbstractFile file = fileService.findById(fileId);
                    Set<VersioningHistory> versions = versioningHistoryService.findByFileId(file.getId());
                    versions.forEach(System.out::println);
                }
                case 4 -> {
                    System.out.print("ID da versão: ");
                    Long versionId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Nova mensagem: ");
                    String newMessage = scanner.nextLine();
                    versioningHistoryService.updateVersionMessage(versionId, newMessage);
                    System.out.println("Mensagem atualizada com sucesso!");
                }
                case 0 -> {
                    userService.logout();
                    back = true;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void showDetails(AbstractFile fileOpt){
        System.out.println("\n=== DETALHES DO ARQUIVO ===");
        System.out.println("Nome: " + fileOpt.getName());
        System.out.println("Tipo: " + fileOpt.getClass().getSimpleName());
        System.out.println("Dono: " + fileOpt.getUser().getUsername());
        System.out.println("Criado em: " + formatDate(fileOpt.getCreatedAt()));
        if(fileOpt.getLastModifier() != null){
            System.out.println("Modificado em: " + formatDate(fileOpt.getLastModifier()));
        }

        // Mostra informações específicas para vídeos
        if (fileOpt instanceof VideoFile) {
            System.out.println("Duração: " + ((VideoFile) fileOpt).getDuration() + " segundos");
        }

        System.out.println("Tamanho: " + fileOpt.getFileMetaData().getFileSize() + " bytes");

        // Mostra versões (apenas os comentários)
        if (!fileOpt.getVersions().isEmpty()) {
            System.out.println("\nHistórico de versões:");
            fileOpt.getVersions().forEach(version -> {
                System.out.println("- " + formatDate(version.getCreationDate()) +
                        ": " + version.getCommitMessage());
            });
        }

        // Mostra comentários
        if (!fileOpt.getFileComments().isEmpty()) {
            System.out.println("\nComentários:");
            fileOpt.getFileComments().forEach(comment -> {
                System.out.println("- " + comment.getAuthor().getUsername() +
                        " (" + formatDate(comment.getCreatedAt()) + "): " +
                        (comment.getBody().length() > 50 ?
                                comment.getBody().substring(0, 50) + "..." :
                                comment.getBody()));
            });
        }

        // Mostra operações (apenas tipo e data)
        if (!fileOpt.getOperations().isEmpty()) {
            System.out.println("\nÚltimas operações:");
            fileOpt.getOperations().stream()
                    .limit(5) // Mostra apenas as 5 últimas
                    .forEach(op -> {
                        System.out.println("- " + op.getOperationType() +
                                " em " + formatDate(op.getOperationDate()));
                    });
        }
    }

    private String formatDate(Instant date) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(date);
    }
}