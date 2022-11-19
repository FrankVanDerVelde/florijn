package com.hva.ewa.team2.backend.domain.models.hourregistration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
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
    @JsonProperty("participant")
    private ProjectParticipant projectParticipant;

    @Getter
    private int projectId;

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

    public HourRegistration(int id, int projectId, ProjectParticipant participant, LocalDateTime from, LocalDateTime to, String description) {
        this(id, projectId, participant, from, to, description, null);
    }

    public HourRegistration(int id, int projectId, ProjectParticipant participant, LocalDateTime from, LocalDateTime to, String description, Status status) {
        this.id = id;
        this.projectId = projectId;
        this.projectParticipant = participant;
        this.from = from;
        this.to = to;
        this.description = description;
        this.status = status == null ? Optional.empty() : Optional.of(status);
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
