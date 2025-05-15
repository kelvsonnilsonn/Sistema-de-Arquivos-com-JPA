package com.orizon.webdriver.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.Duration;

@Getter
@Setter
public class Plan {

    private long id;
    private String name;
    private String userSpace;
    private Duration duration;
    private Date aquisitionDate;

    public Plan(String name, String userSpace) {
        this.name = name;
        this.userSpace = userSpace;
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
                duration != null ? formatDuration(duration) : "Não definida",
                aquisitionDate != null ? aquisitionDate.toString() : "Não registrada"
        );
    }

    private String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();

        return String.format("%d dias, %02dh %02dm", days, hours, minutes);
    }
}