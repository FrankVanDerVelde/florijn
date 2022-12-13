package com.hva.ewa.team2.backend.domain.models.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * A project participant is a specialist that is assigned to a project.
 * An hourly rate is bound to a project participant and can differ between specialists.
 */
@Entity
public class ProjectParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private int projectId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty("user")
    private Specialist specialist;

    @Getter
    @Setter
    private String role;

    @Getter
    @Setter
    private double hourlyRate;

    public ProjectParticipant() {
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public ProjectParticipant(Specialist specialist, String role, double hourlyRate) {
        this.specialist = specialist;
        this.role = role;
        this.hourlyRate = hourlyRate;
    }

}
