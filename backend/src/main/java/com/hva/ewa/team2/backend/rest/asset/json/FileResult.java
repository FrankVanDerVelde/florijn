package com.hva.ewa.team2.backend.rest.asset.json;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

public class FileResult {

    @Getter
    @Setter
    private String path;

    @Getter
    @Setter
    private String absolutePath;

    public FileResult(Path absolutePath, String path) {
        this.path = path;
        this.absolutePath = absolutePath.toString();
    }
}
