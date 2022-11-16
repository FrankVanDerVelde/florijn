package com.hva.ewa.team2.backend.domain.models.hourregistration;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;

public class HourRegistrationRow {

    @Getter @Setter
    private int id;
    @Getter @Setter
    private int projectId;
    @Getter @Setter
    private int hour_registration_id;

    @Getter @Setter
    private Optional<Date> from;
    @Getter @Setter
    private Optional<Date> to;
    @Getter @Setter
    private boolean isApproved;

    public HourRegistrationRow(int id, int projectId, int hour_registration_id, Optional<Date> from, Optional<Date> to, boolean isApproved) {
        this.id = id;
        this.projectId = projectId;
        this.hour_registration_id = hour_registration_id;
        this.from = from;
        this.to = to;
        this.isApproved = isApproved;
    }

    public HourRegistrationRow(int id, int projectId, int hour_registration_id) {
        this.id = id;
        this.projectId = projectId;
        this.hour_registration_id = hour_registration_id;
        this.from = Optional.empty();
        this.to = Optional.empty();
        this.isApproved = false;
    }
}
