package com.hva.ewa.team2.backend.domain.models.project;

import lombok.Getter;

public class ProjectReport {

    @Getter
    private final String title;
    @Getter
    private final String value;

    public ProjectReport(String title, String value) {
        this.title = title;
        this.value = value;
    }

}
