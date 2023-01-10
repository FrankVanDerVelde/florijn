package com.hva.ewa.team2.backend.domain.models.availability;

import com.hva.ewa.team2.backend.domain.models.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Availability {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
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

    public Availability(int id, User user, LocalDate date, LocalTime from, LocalTime to) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public Availability(User user, LocalDate date, LocalTime from, LocalTime to) {
        this.user = user;
        this.date = date;
        this.from = from;
        this.to = to;
    }
    public Availability() {

    }

    @Override
    public String toString() {
        return "Availability{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
