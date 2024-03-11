package com.hva.ewa.team2.backend.domain.models.hourregistration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.rest.hourregistration.HourRegistrationViews;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    @JsonView(HourRegistrationViews.HourRegistrationProject.class)
    private Project project;

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

    public HourRegistration(int id, Project project, ProjectParticipant participant, LocalDateTime from, LocalDateTime to, String description) {
        this(id, project, participant, from, to, description, null);
    }

    public HourRegistration(int id, Project project, ProjectParticipant participant, LocalDateTime from, LocalDateTime to, String description, Status status) {
        this.id = id;
        this.project = project;
        this.projectParticipant = participant;
        this.from = from;
        this.to = to;
        this.description = description;
        this.status = Optional.ofNullable(status);
    }

    public HourRegistration() {
        this.status = Optional.empty();
    }

    public enum Status {
        ACCEPTED,
        REJECTED
    }

    public void setStatus(Status status) {
        this.status = Optional.ofNullable(status);
    }

    public boolean isAccepted() {
        if (status.isPresent()) {
            return this.status.get() == Status.ACCEPTED;
        } else {
            return false;
        }
    }

    public double getHoursSpent() {
        return ChronoUnit.MINUTES.between(from, to) / 60d;
    }

    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

}
