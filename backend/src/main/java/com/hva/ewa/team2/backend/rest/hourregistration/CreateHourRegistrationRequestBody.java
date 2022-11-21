package com.hva.ewa.team2.backend.rest.hourregistration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CreateHourRegistrationRequestBody {

    @Getter @Setter
    @JsonProperty("project_id")
    private int projectId;

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime from;

    @Getter @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime to;

    @Getter @Setter
    private String description;

    public CreateHourRegistrationRequestBody(int projectId, LocalDateTime from, LocalDateTime to, String description) {
        this.projectId = projectId;
        this.from = from;
        this.to = to;
        this.description = description;
    }

    public CreateHourRegistrationRequestBody() { }
}
