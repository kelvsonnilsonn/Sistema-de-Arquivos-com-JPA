package com.orizon.webdriver.domain.model.file;

import com.orizon.webdriver.domain.model.file.filedatas.FileInformations;

import java.time.Duration;

public final class VideoFile extends AbstractFile{

    private final Duration duration;
    private final FileType fileType;

    public VideoFile(FileInformations fileInformations, Duration duration) {
        super(fileInformations);
        this.duration = duration;
        this.fileType = FileType.VIDEO;
    }

    @Override
    public AFileInterface load() {
        return this;
    }
}
