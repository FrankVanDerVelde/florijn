package com.hva.ewa.team2.backend.domain.models.user;

public class Admin extends User{

    private String firstName;
    private String lastName;

    public Admin(int id, String email, String profilePictureURL, String firstName, String lastName) {
        super(id, email, profilePictureURL);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
