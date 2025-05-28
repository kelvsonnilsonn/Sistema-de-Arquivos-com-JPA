package com.orizon.webdriver.domain.model;

import com.orizon.webdriver.domain.model.file.AbstractFile;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "permissions")
@Getter
@Setter
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private AbstractFile file;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private AbstractUser receiver;

    @Column(nullable = false)
    private String type;

    public Permission(AbstractFile file, AbstractUser receiver, PermissionType type) {
        this.file = file;
        this.receiver = receiver;
        this.type = type.getDescription();
    }

    @Getter
    public enum PermissionType{
        SAVE("Salvar"),
        DELETE("Deletar"),
        LOAD("Carregar"),
        EDIT("Editar");

        private final String description;

        PermissionType(String description) {
            this.description = description;
        }
    }
}
