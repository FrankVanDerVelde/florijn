package com.hva.ewa.team2.backend.rest.project.request;

import lombok.Getter;
import lombok.Setter;

public class ProjectEditVerificationRequest {

    @Getter
    @Setter
    private String title;

    public ProjectEditVerificationRequest() {
    }

    public ProjectEditVerificationRequest(String title) {
        this.title = title;
    }
}
