package com.hva.ewa.team2.backend.domain.models.user;

import lombok.Getter;
import lombok.Setter;

public abstract class User {

    @Getter
    @Setter
    protected int id;
    @Getter
    @Setter
    protected String email;
    @Getter
    @Setter
    protected String avatarUrl;
    @Getter
    @Setter
    protected String password;

    public User(int id, String email, String password, String profilePictureURL) {
        this.id = id;
        this.email = email;
        this.avatarUrl = profilePictureURL;
        this.password = password;
    }
}
