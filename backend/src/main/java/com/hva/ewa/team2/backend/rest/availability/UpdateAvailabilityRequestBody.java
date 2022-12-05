package com.hva.ewa.team2.backend.rest.availability;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateAvailabilityRequestBody {

    @Getter @Setter
    private LocalDate date;
    @Getter @Setter
    private LocalTime from;
    @Getter @Setter
    private LocalTime to;

    public UpdateAvailabilityRequestBody(LocalDate date, LocalTime from, LocalTime to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }

    UpdateAvailabilityRequestBody() {
    }
}
