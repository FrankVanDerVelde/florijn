package com.hva.ewa.team2.backend.rest.project.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Class with properties required to update a project.
 * Used for deserializing JSON.
 */
public class ProjectInfoRequest {

    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private Optional<Integer> client;
    @Getter
    @Setter
    private MultipartFile logoFile;

    public ProjectInfoRequest() {}

    public ProjectInfoRequest(String title, String description, Optional<Integer> client, MultipartFile logoFile) {
        this.title = title;
        this.description = description;
        this.client = client;
        this.logoFile = logoFile;
    }

    @Override
    public String toString() {
        return "JsonProjectInfo{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", client=" + client +
                ", logoFile=" + logoFile +
                '}';
    }

}
