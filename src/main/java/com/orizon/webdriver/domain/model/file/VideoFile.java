package com.orizon.webdriver.domain.model.file;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Setter
@Getter
@Entity
@DiscriminatorValue("Video")
public class VideoFile extends AbstractFile{

    private Duration duration;

    protected VideoFile(){}

    public VideoFile(String fileMetaData, Duration duration) {
        super(fileMetaData);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                "Duração: %s minutos\n",
                duration != null ? duration.toMinutes() : "N/A"
        );
    }
}
