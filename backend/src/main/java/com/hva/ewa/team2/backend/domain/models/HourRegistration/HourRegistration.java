package com.hva.ewa.team2.backend.domain.models.HourRegistration;

import lombok.Getter;
import lombok.Setter;

public class HourRegistration {

    @Getter @Setter
    private final int id;
    @Getter @Setter
    private final int projectId;

    HourRegistration(int id, int projectId) {
        this.id = id;
        this.projectId = projectId;
    }
}
