package com.hva.ewa.team2.backend.rest.user.json;

import lombok.Getter;

public class JsonUserInfo {
    @Getter
    private final int id;
    @Getter
    private final String role;

    public JsonUserInfo(int id, String role) {
        this.id = id;
        this.role = role;
    }
}
