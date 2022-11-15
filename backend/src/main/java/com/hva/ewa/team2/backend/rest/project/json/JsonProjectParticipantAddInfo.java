package com.hva.ewa.team2.backend.rest.project.json;

import lombok.Getter;

/**
 * Class with properties required to add a project participant.
 * Used for deserializing JSON.
 */
public class JsonProjectParticipantAddInfo {

    @Getter
    private int userId = -1;
    @Getter
    private String role = "";
    @Getter
    private double hourlyRate = -1;

    public JsonProjectParticipantAddInfo() {}

    public JsonProjectParticipantAddInfo(int userId, String role, double hourlyRate) {
        this.userId = userId;
        this.role = role;
        this.hourlyRate = hourlyRate;
    }
}
