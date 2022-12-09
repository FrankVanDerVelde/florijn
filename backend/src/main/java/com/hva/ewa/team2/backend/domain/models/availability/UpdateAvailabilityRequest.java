package com.hva.ewa.team2.backend.domain.models.availability;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateAvailabilityRequest {

    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private LocalDate date;
    @Getter
    @Setter
    private LocalTime from;
    @Getter
    @Setter
    private LocalTime to;

    public UpdateAvailabilityRequest(int id, LocalDate date, LocalTime from, LocalTime to) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
    }

}
