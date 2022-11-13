package com.hva.ewa.team2.backend.domain.models.user;

public abstract class User {

    protected int id;
    protected String email;
    protected String profilePictureURL;

    public User(int id, String email, String profilePictureURL) {
        this.id = id;
        this.email = email;
        this.profilePictureURL = profilePictureURL;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }
}
