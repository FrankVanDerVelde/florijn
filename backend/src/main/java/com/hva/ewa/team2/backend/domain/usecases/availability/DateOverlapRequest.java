package com.hva.ewa.team2.backend.domain.usecases.availability;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateOverlapRequest {

    @Getter
    @Setter
//    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @Getter
    @Setter
//    @JsonFormat(pattern="HH:mm")
    private LocalTime from;

    @Getter
    @Setter
//    @JsonFormat(pattern="HH:mm")
    private LocalTime to;

    public DateOverlapRequest(LocalDate date, LocalTime from, LocalTime to) {
        this.date = date;
        this.from = from;
        this.to = to;
    }


}
