package com.hva.ewa.team2.backend.rest.user.json;

import lombok.Getter;
import lombok.Setter;

public class JsonSpecialistInfo {
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
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;

    public JsonSpecialistInfo(){}

    public JsonSpecialistInfo(int id, String email, String password, String profilePictureURL, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.avatarUrl = profilePictureURL;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
