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
    @Getter
    protected final Role role;

    public User(int id, String email, String password, String profilePictureURL, Role role) {
        this.id = id;
        this.email = email;
        this.avatarUrl = profilePictureURL;
        this.password = password;
        this.role = role;
    }

    public enum Role {
        ADMIN,
        SPECIALIST,
        CLIENT
    }

}
