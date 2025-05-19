package com.orizon.webdriver.domain.model;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "file_operations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileOperation {

    /*
     *   Relação muitos-para-muitos respeita o padrão de boa prática de:
     *
     *       - Criar uma classe para armazenar
     *       - Criar uma relação muitos-para-um com os alvos (AbstractFile/User)
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id", nullable = false)
    private AbstractFile file;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private AbstractUser user;

    @Column(name = "operation_date", nullable = false)
    private LocalDateTime operationDate;

    @Column(name = "operation_type", nullable = false)
    private OperationType operationType;

    public FileOperation(AbstractFile file, AbstractUser user, OperationType operationType) {
        this.file = Objects.requireNonNull(file);
        this.user = Objects.requireNonNull(user);
        this.operationType = operationType;
        this.operationDate = LocalDateTime.now();
    }

    public enum OperationType {
        EDIT("Edição"),
        VIEW("Visualização"),
        DOWNLOAD("Download"),
        SHARE("Compartilhamento");

        private final String description;

        OperationType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return String.format(
                """
                🔄 Operação em Arquivo
                🆔 ID: %d
                📄 Arquivo: %s (ID: %s)
                👤 Usuário: %s (ID: %s)
                🏷️ Tipo: %s
                ⏰ Data/Hora: %s
                """,
                id,
                file != null ? file.getFileName() : "N/A",
                file != null ? file.getId() : "N/A",
                user != null ? user.getUserLogin() : "N/A",
                user != null ? user.getId() : "N/A",
                operationType.name(),
                operationDate.format(dateFormatter)
        );
    }
}
