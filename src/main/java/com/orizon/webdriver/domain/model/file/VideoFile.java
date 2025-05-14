package com.orizon.webdriver.domain.model.file;

import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.infrastructure.repository.FileRepository;
import lombok.Setter;

import java.time.Duration;

@Setter
public final class VideoFile extends AbstractFile{

    private final Duration duration;

    public VideoFile(FileMetaData fileMetaData, Duration duration) {
        super(fileMetaData);
        this.duration = duration;
    }

    @Override
    public FileOperations load() {
        return this;
    }

    @Override
    public void save(FileRepository fileRepository) {

    }

    @Override
    public void delete(FileRepository fileRepository) {

    }

    @Override
    public String toString() {
        return super.toString() + String.format(
                "Duração: %s minutos\n",
                duration != null ? duration.toMinutes() : "N/A"
        );
    }
}
