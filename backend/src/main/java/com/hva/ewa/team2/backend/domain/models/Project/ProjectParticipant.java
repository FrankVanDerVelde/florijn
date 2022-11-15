package com.hva.ewa.team2.backend.domain.models.Project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import lombok.Getter;
import lombok.Setter;

/**
 * A project participant is a specialist that is assigned to a project.
 * An hourly rate is bound to a project participant and can differ between specialists.
 */
public class ProjectParticipant {

    @Getter
    @JsonProperty("user")
    private final Specialist specialist;
    @Getter @Setter
    private String role;
    @Getter @Setter
    private double hourlyRate;

    public ProjectParticipant(Specialist specialist, String role, double hourlyRate) {
        this.specialist = specialist;
        this.role = role;
        this.hourlyRate = hourlyRate;
    }

}
