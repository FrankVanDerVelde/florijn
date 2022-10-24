package com.hva.ewa.team2.backend.domain.usecases.HourRegistration;

import lombok.Getter;
import lombok.NonNull;

import java.util.Date;

public class CreateHourRegistrationRequest {

    @Getter
    private int projectId;
    @Getter
    private Date from;
    @Getter
    private Date to;

    public CreateHourRegistrationRequest(@NonNull int projectId, Date from, Date to) {
        this.projectId = projectId;
        this.from = from;
        this.to = to;
    }
}
