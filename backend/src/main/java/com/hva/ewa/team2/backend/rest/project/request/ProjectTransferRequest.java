package com.hva.ewa.team2.backend.rest.project.request;

import lombok.Getter;
import lombok.Setter;

public class ProjectTransferRequest extends ProjectEditVerificationRequest {

    @Getter
    @Setter
    private int clientId;

    public ProjectTransferRequest() {
    }

    public ProjectTransferRequest(String title) {
        super(title);
    }
}
