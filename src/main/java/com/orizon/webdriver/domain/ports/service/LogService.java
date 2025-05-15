package com.orizon.webdriver.domain.ports.service;

import com.orizon.webdriver.domain.model.Comment;
import com.orizon.webdriver.domain.ports.file.FileOperations;
import com.orizon.webdriver.domain.service.LogServiceImpl;

public interface LogService {

    void log(FileOperations file, LogServiceImpl.LogType type);
    void log(Comment comment);
}
