package com.hva.ewa.team2.backend.rest.User.json;

import lombok.Getter;

public class JsonUserInfo {
    @Getter
    private long id;
    @Getter
    private String role;

    public JsonUserInfo(long id, String role) {
        this.id = id;
        this.role = role;
    }
}
