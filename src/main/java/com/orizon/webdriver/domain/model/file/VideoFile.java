package com.orizon.webdriver.domain.model.file;

import com.orizon.webdriver.domain.model.file.data.FileInformations;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;
import com.orizon.webdriver.domain.model.user.uinterface.AUserInterface;
import com.orizon.webdriver.domain.repository.FileRepository;
import lombok.Setter;

import java.time.Duration;

@Setter
public final class VideoFile extends AbstractFile{

    private final Duration duration;

    public VideoFile(FileInformations fileInformations, Duration duration) {
        super(fileInformations);
        this.duration = duration;
    }

    @Override
    public AFileInterface load() {
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
