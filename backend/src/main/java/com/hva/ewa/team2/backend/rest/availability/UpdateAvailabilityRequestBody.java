package com.hva.ewa.team2.backend.rest.availability;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateAvailabilityRequestBody {

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @Getter @Setter
    @JsonFormat(pattern="HH:mm")
    private LocalTime from;

    @Getter @Setter
    @JsonFormat(pattern="HH:mm")
    private LocalTime to;

    public UpdateAvailabilityRequestBody(LocalDate date, LocalTime from, LocalTime to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }

    UpdateAvailabilityRequestBody() {
    }


}
