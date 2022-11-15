package com.hva.ewa.team2.backend.domain.models.HourRegistration;

import lombok.Getter;
import lombok.Setter;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class HourRegistration {

    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private long projectId;

    @Getter
    @Setter
    private long userId;

    @Getter
    @Setter
    private LocalDateTime from;

    @Getter
    @Setter
    private LocalDateTime to;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private boolean isAccepted;

    public HourRegistration(long id, long projectId, long userId, LocalDateTime from, LocalDateTime to, String description) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.description = description;
        this.isAccepted = false;
    }

    public HourRegistration(long projectId, long userId, LocalDateTime from, LocalDateTime to, String description) {
        this.projectId = projectId;
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.description = description;
        this.isAccepted = false;
    }
}
