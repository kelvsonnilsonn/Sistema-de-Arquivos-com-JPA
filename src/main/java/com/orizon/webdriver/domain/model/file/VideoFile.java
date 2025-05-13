package com.orizon.webdriver.domain.model.file;

import com.orizon.webdriver.domain.model.file.filedatas.FileInformations;
import com.orizon.webdriver.domain.model.file.finterface.AFileInterface;

import java.time.Duration;

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
    public String toString() {
        return super.toString() + String.format(
                "Duração: %s minutos\n",
                duration != null ? duration.toMinutes() : "N/A"
        );
    }
}
