package com.hva.ewa.team2.backend.domain.models.User;

public abstract class User {

    protected int id;
    protected String email;
    protected String password;
    protected String profilePictureURL;

    public User(int id, String email, String password, String profilePictureURL) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profilePictureURL = profilePictureURL;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }
}
