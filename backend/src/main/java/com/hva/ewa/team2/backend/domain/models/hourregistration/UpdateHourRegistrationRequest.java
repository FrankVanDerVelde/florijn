package com.hva.ewa.team2.backend.domain.models.hourregistration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hva.ewa.team2.backend.rest.hourregistration.UpdateHourRegistrationRequestBody;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class UpdateHourRegistrationRequest {

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private int projectId;

    @Getter
    @Setter
    @JsonProperty("user_id")
    private int userId;

    @Getter @Setter
    private LocalDateTime from;

    @Getter @Setter
    private LocalDateTime to;

    @Getter @Setter
    private String description;

    public UpdateHourRegistrationRequest(int id, int projectId, int userId, LocalDateTime from, LocalDateTime to, String description) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.from = from;
        this.to = to;
        this.description = description;
    }

    public static UpdateHourRegistrationRequest fromBody(UpdateHourRegistrationRequestBody body, int id) {
        return new UpdateHourRegistrationRequest(
                id,
                body.getProjectId(),
                body.getUserId(),
                body.getFrom(),
                body.getTo(),
                body.getDescription()
        );
    }
}
