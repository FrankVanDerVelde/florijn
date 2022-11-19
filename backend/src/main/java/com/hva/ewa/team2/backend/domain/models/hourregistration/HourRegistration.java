package com.hva.ewa.team2.backend.domain.models.hourregistration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

public class HourRegistration {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private Project project;

    @Getter
    @Setter
    private long userId;

    @Getter
    @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime from;

    @Getter
    @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime to;

    @Getter
    @Setter
    private String description;

    @Getter
    private Optional<Status> status;

    public HourRegistration(int id, Project project, long userId, LocalDateTime from, LocalDateTime to, String description) {
        this.id = id;
        this.project = project;
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.description = description;
        this.status = Optional.empty();
    }

    public HourRegistration(Project project, long userId, LocalDateTime from, LocalDateTime to, String description) {
        this.project = project;
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.description = description;
        this.status = Optional.empty();
    }

    public HourRegistration() {
        this.status = Optional.empty();
    }

    public enum Status {
        ACCEPTED,
        REJECTED
    }

    public void setStatus(Status status) {
        this.status = Optional.of(status);
    }

    public boolean isAccepted() {
        if (status.isPresent()) {
            return this.status.get() == Status.ACCEPTED;
        } else {
            return false;
        }
    }
}
