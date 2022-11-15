package com.hva.ewa.team2.backend.domain.models.HourRegistration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CreateHourRegistrationRequest {

    @Getter @Setter
    private long projectId;

    @Getter @Setter
    private long userId;

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime from;

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime to;

    @Getter @Setter
    private String description;

    public CreateHourRegistrationRequest(long projectId, long userId, LocalDateTime from, LocalDateTime to, String description) {
        this.projectId = projectId;
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.description = description;
    }

    public HourRegistration toDomainModel(long nextId) {
        return new HourRegistration(nextId, projectId, userId, from, to, description);
    }
}
