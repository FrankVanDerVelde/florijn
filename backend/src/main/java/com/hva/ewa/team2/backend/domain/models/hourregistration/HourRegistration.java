package com.hva.ewa.team2.backend.domain.models.hourregistration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.rest.hourregistration.HourRegistrationViews;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class HourRegistration {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @JsonProperty("participant")
    @ManyToOne
//    @JoinColumn(name = "user_id")
    private ProjectParticipant projectParticipant;

    @Getter
    @JsonView(HourRegistrationViews.HourRegistrationProject.class)
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime from;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime to;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

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
        this.status = status;
    }

    public HourRegistration() {
    }

    public enum Status {
        ACCEPTED,
        REJECTED,
    }

    public boolean isAccepted() {
        return this.status == Status.ACCEPTED;
    }

    public double getHoursSpent() {
        return ChronoUnit.MINUTES.between(from, to) / 60d;
    }

}
