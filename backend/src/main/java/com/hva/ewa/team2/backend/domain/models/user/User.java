package com.hva.ewa.team2.backend.domain.models.user;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

public abstract class User {

    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    protected int id;
    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    protected String email;
    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    protected String avatarUrl;
    @Getter
    @Setter
    protected String password;
    @Getter
    @JsonView(EssentialInfo.class)
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

    public class EssentialInfo {

    }

}
