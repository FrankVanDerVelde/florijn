package com.hva.ewa.team2.backend.domain.models.hourregistration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CreateHourRegistrationRequest {

    @Getter @Setter
    private int projectId;

    @Getter @Setter
    private int userId;

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime from;

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime to;

    @Getter @Setter
    private String description;

    public CreateHourRegistrationRequest(int projectId, int userId, LocalDateTime from, LocalDateTime to, String description) {
        this.projectId = projectId;
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.description = description;
    }

    public HourRegistration toDomainModel(int nextId, Project project) {
        return new HourRegistration(nextId, project, project.getParticipantByUserId(userId), from, to, description);
    }
}
