# 📂 Sistema de Gerenciamento de Arquivos
Um sistema completo de gerenciamento de arquivos com controle de permissões, versionamento e suporte integrado, desenvolvido em Java com Spring Framework.

## 🚀 Funcionalidades

### 👥 Gestão de Usuários
- Sistema hierárquico (Administrador/Usuário comum)
- Cadastro e autenticação de usuários
- Controle de permissões granular

### 📁 Gestão de Arquivos
- Suporte a diferentes tipos de arquivos (Texto, Vídeo, Genéricos)
- Upload e organização de arquivos
- Sistema de permissões por arquivo
- Compartilhamento entre usuários

### 🔐 Controle de Acessos
- Permissões específicas (VISUALIZAR, EDITAR, DELETAR, COMPARTILHAR, COMENTAR)
- Controle de acesso por arquivo e usuário
- Herança de permissões

### 🔄 Versionamento
- Histórico completo de alterações
- Mensagens de commit personalizadas
- Controle de versões por arquivo

### 🎫 Sistema de Suporte
- Tickets de suporte técnico
- Atribuição para administradores
- Acompanhamento de status (ABERTO, PENDENTE, RESOLVIDO)

### 💬 Sistema de Comentários
- Comentários em arquivos
- Menções e referências
- Histórico de discussões
  
## 🛠️ Tecnologias Utilizadas
- Java 21
- Spring Framework (Data JPA, Dependency Injection, Components)
- Hibernate ORM com estratégia SINGLE_TABLE para herança
- Spring Data JPA para persistência
- Lombok para redução de boilerplate code
- Maven para gerenciamento de dependências

## 🏗️ Estrutura do Projeto
```text
src/
├── main/
│   ├── java/com/orizon/webdriver/
│   │   ├── application/          # Camada de aplicação (CLI)
│   │   ├── domain/
│   │   │   ├── model/           # Entidades do domínio
│   │   │   ├── services/        # Serviços de domínio
│   │   │   ├── valueobjects/    # Objetos de valor
│   │   │   └── exceptions/      # Exceções personalizadas
│   │   ├── infra/
│   │   │   └── persistence/     # Repositórios e persistência
│   │   └── WebDriverApplication.java
│   └── resources/
│       └── application.properties
```

## ⚠️ Sistema de Exceções Personalizadas
O projeto possui um sistema robusto de exceções personalizadas para tratamento específico de erros:

### 🔒 Exceções de Arquivo
- DuplicatedFileException -> Tentativa de inserir arquivo duplicado
- InvalidFileTypeException -> Tipo de arquivo não suportado
- FileNotFoundException -> Arquivo não encontrado

### 👤 Exceções de Usuário
- UserNotFoundException -> Usuário não encontrado
- InvalidCredentialsException -> Credenciais inválidas
- UserNotAuthorizedException -> Usuário sem permissão

### 🏢 Exceções de Instituição
- InstitutionNotFoundException -> Instituição não encontrada
- InstitutionLimitException -> Limite de instituições excedido

### 📋 Exceções de Plano
- PlanNotFoundException -> Plano não encontrado
- PlanLimitExceededException -> Limite do plano excedido
- PlanInvalidLimitException -> Limite inválido especificado

### 🔧 Exceções de Operações
- ENFieldException -> Campo obrigatório não preenchido
- InvalidOperationException -> Operação inválida
- CommentInexistentException -> Comentário não encontrado

### 🎫 Exceções de Suporte
- SupportInexistentException -> Ticket não existe
- SupportNotFoundException -> Ticket de suporte não encontrado
- SupportAlreadyClosedException -> Tentativa de modificar ticket fechado

## 📦 Principais Entidades
- AbstractUser -> Classe base para usuários
- Administrator -> Usuário com privilégios administrativos
- AbstractFile -> Classe base para arquivos
- VideoFile/GenericFile -> Tipos específicos de arquivos
- Permission -> Controle de permissões
- VersioningHistory -> Histórico de versões
- Support -> Tickets de suporte
- Comment -> Sistema de comentários

## 🚀 Como Executar
### Pré-requisitos
- JDK 21+
- Maven 3.6+
- Banco de dados configurado (configuração em application.properties)

### Instalação
```text
bash
# Clone o repositório
git clone https://github.com/seu-usuario/file-management-system.git

# Acesse o diretório
cd file-management-system

# Compile o projeto
mvn clean compile

# Execute a aplicação
mvn spring-boot:run
```

## ⚙️ Configuração
Configure o banco de dados no arquivo application.properties:

### MySQL:
```text
spring.datasource.url=jdbc:mysql://localhost:3306/filemanager
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

### PostgreSQL:
```text
spring.datasource.url=jdbc:postgresql://localhost:5432/filemanager
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```

## 🎯 Exemplos de Uso
- O sistema oferece interface de linha de comando (CLI) com menus interativos
1. Login/Cadastro de usuários
2. Upload e gestão de arquivos
3. Compartilhamento com outros usuários
4. Controle de versões com mensagens de commit
5. Sistema de tickets de suporte

## 👨‍💻 Autor
-> Kelvson Nilson
## GitHub: 
-> @kelvsonnilsonn
## LinkedIn: 
-> Kelvson Nilson
