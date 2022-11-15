package com.hva.ewa.team2.backend.rest.HourRegistration;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CreateHourRegistrationRequestBody {

    @Getter @Setter
    private long projectId;

    @Getter @Setter
    private LocalDateTime from;

    @Getter @Setter
    private LocalDateTime to;

    @Getter @Setter
    private String description;

    public CreateHourRegistrationRequestBody(long projectId, LocalDateTime from, LocalDateTime to, String description) {
        this.projectId = projectId;
        this.from = from;
        this.to = to;
        this.description = description;
    }
}
