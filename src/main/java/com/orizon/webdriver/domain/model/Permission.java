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

    @Column(nullable = false)
    private Long fileId;

    @Column(nullable = false)
    private Long receiverId;

    @Column(nullable = false)
    private String type;

    public Permission(Long fileId, Long receiverId, PermissionType type) {
        this.fileId = fileId;
        this.receiverId = receiverId;
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
