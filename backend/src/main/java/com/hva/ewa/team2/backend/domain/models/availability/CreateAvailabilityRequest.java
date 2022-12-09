package com.hva.ewa.team2.backend.domain.models.availability;

import com.hva.ewa.team2.backend.domain.models.availability.Availability;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class CreateAvailabilityRequest {

    @Getter
    @Setter
    int userId;
    @Getter
    @Setter
    private LocalDate date;

    @Getter
    @Setter
    private LocalTime from;

    @Getter
    @Setter
    private LocalTime to;

    public CreateAvailabilityRequest(int userId, LocalDate date, LocalTime from, LocalTime to) {
        this.userId = userId;
        this.date = date;
        this.from = from;
        this.to = to;
    }


}
