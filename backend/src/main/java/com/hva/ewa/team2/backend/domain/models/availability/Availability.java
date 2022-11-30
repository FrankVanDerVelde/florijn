package com.hva.ewa.team2.backend.domain.models.availability;

import com.hva.ewa.team2.backend.domain.models.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

public class Availability {

    @Getter
    @Setter
    private int id;

    @Getter @Setter
    private User user;

    @Getter
    @Setter
    private LocalDate date;

    @Getter
    @Setter
    private LocalTime from;

    @Getter
    @Setter
    private LocalTime to;

    public dAvailability(int id, User user, LocalDate date, LocalTime from, LocalTime to) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.from = from;
        this.to = to;
    }
}
