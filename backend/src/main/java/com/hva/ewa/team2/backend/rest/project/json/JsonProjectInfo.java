package com.hva.ewa.team2.backend.rest.project.json;

import lombok.Getter;

/**
 * Class with properties required to update a project.
 * Used for deserializing JSON.
 */
public class JsonProjectInfo {

    @Getter
    private String title = "";
    @Getter
    private String description = "";
    @Getter
    private int client = -1;
    @Getter
    private String logoSrc = null;

    public JsonProjectInfo() {}

    public JsonProjectInfo(String title, String description, int client, String logoSrc) {
        this.title = title;
        this.description = description;
        this.client = client;
        this.logoSrc = logoSrc;
    }
}
