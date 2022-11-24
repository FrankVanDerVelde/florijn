package com.hva.ewa.team2.backend.rest.user.json;

import lombok.Getter;

public class JsonUserRole {

    @Getter
    private final String role;

    public JsonUserRole(String role) {
        this.role = role;
    }
}

