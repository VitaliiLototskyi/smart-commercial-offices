package com.microservices.apigateway.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileResourceService {

    private final ResourceLoader resourceLoader;

    public File loadResourceFile(String filePath) {
        File searchedFile = null;
        try {
            searchedFile = resourceLoader.getResource("classpath:" + filePath).getFile();
        } catch (IOException e) {
            log.warn(String.format("Resource file %s wasn't loaded", filePath), e);
        }
        return searchedFile;
    }

    public List<String> readResourceFileLines(String filePath) {
        try {
            return Files.readAllLines(this.loadResourceFile(filePath).toPath());
        } catch (IOException e) {
            log.warn(String.format("Error reading lines from resource file %s", filePath), e);
            return new ArrayList<>();
        }
    }
}
