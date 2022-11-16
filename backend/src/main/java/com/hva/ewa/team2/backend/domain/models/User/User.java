package com.hva.ewa.team2.backend.domain.models.User;

import lombok.Getter;

public abstract class User {

    @Getter
    protected int id;
    @Getter
    protected String email;
    @Getter
    protected String avatarUrl;
    @Getter
    protected String password;

    public User(int id, String email, String password, String profilePictureURL) {
        this.id = id;
        this.email = email;
        this.avatarUrl = profilePictureURL;
        this.password = password;
    }
}
