package com.hva.ewa.team2.backend.rest.HourRegistration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CreateHourRegistrationRequestBody {

    @Getter @Setter
    @JsonProperty("project_id")
    private long projectId;

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime from;

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime to;

    @Getter @Setter
    private String description;

    public CreateHourRegistrationRequestBody(long projectId, LocalDateTime from, LocalDateTime to, String description) {
        this.projectId = projectId;
        this.from = from;
        this.to = to;
        this.description = description;
    }

    public CreateHourRegistrationRequestBody() {
    }
}
