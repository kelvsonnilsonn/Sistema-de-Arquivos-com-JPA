package com.orizon.webdriver.domain.model;


import com.orizon.webdriver.domain.exceptions.ENFieldException;
import com.orizon.webdriver.domain.model.user.AbstractUser;
import com.orizon.webdriver.domain.model.user.Administrator;
import com.orizon.webdriver.domain.model.user.User;
import com.orizon.webdriver.domain.valueobjects.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="institutions")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "institution_name",nullable = false)
    private String name;

    @Column(name = "social_cause", nullable = false)
    private String socialCause;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Getter
    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Administrator> admins = new HashSet<>();

    public Institution(String name, String socialCause){
        this.name = Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        this.socialCause = Objects.requireNonNull(socialCause, () -> {throw new ENFieldException();});
    }

    public void addUser(User user){
        if(users.add(user)){
            user.setInstitution(this);
        }
    }

    public void addAdmin(Administrator admin){
        if(admins.add(admin)){
            admin.setInstitution(this);
        }
    }

    public void setZipCode(String zipcode){ this.address.setZipCode(zipcode);}

    @Override
    public String toString() {
        return String.format(
                """
                🏛️ Instituição: %s
                🆔 ID: %d
                🎯 Causa Social: %s
                📍 Endereço: %s
                📋 Plano: %s
                👥 Usuários Vinculados: %d
                """,
                name,
                id,
                socialCause,
                address != null ? address.toString().replace("\n", "\n    ") : "Não informado",
                getPlan() != null ? getPlan().getName() : "Nenhum plano associado",
                users.size()
        );
    }
}
