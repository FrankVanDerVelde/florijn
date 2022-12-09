package com.hva.ewa.team2.backend.rest.availability;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAvailabilityRequestBody {

    @Getter
    @Setter
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @Getter
    @Setter
    @JsonFormat(pattern="HH:mm")
    private LocalTime from;

    @Getter
    @Setter
    @JsonFormat(pattern="HH:mm")
    private LocalTime to;

    public CreateAvailabilityRequestBody(LocalDate date, LocalTime from, LocalTime to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }

    CreateAvailabilityRequestBody() {
    }
}
