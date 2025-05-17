package com.orizon.webdriver.domain.model;

import com.orizon.webdriver.domain.exceptions.ENFieldException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "plans")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name", nullable = false)
    private String name;

    @Column(name = "user_space", nullable = false)
    private String userSpace;

    @Column(name = "duration_in_seconds")
    private Long durationInSeconds;

    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
    private Set<Institution> institutions = new HashSet<>();

    public Plan(String name, String userSpace) {
        this.name = Objects.requireNonNull(name, () -> {throw new ENFieldException();});
        this.userSpace = Objects.requireNonNull(userSpace, () -> {throw new ENFieldException();});
    }

    @Override
    public String toString() {
        return String.format(
                """
                        📋 Plano: %s
                        🆔 ID: %d
                        💾 Espaço do Usuário: %s
                        ⏳ Duração: %s
                        📅 Data de Aquisição: %s
                        """,
                name,
                id,
                userSpace,
                durationInSeconds != null ? formatDuration(durationInSeconds) : "Não definida",
                acquisitionDate != null ? acquisitionDate.toString() : "Não registrada"
        );
    }

    private String formatDuration(Long durationInSeconds) {
        Duration duration = Duration.ofSeconds(durationInSeconds);

        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();

        return String.format("%d dias, %02dh %02dm", days, hours, minutes);
    }
}