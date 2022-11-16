package com.hva.ewa.team2.backend.domain.models.user;

import lombok.Getter;

public abstract class User {

    @Getter
    protected int id;
    @Getter
    protected String email;
    @Getter
    protected String avatarUrl;

    public User(int id, String email, String profilePictureURL) {
        this.id = id;
        this.email = email;
        this.avatarUrl = profilePictureURL;
    }

}
