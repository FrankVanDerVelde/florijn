package com.hva.ewa.team2.backend.rest.User.json;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JsonCredentials {
    @Getter
    private String email;
    @Getter
    private String password;

    public JsonCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
