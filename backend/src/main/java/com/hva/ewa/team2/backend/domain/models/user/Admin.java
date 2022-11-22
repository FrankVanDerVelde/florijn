package com.hva.ewa.team2.backend.domain.models.user;

import lombok.Getter;
import lombok.Setter;

public class Admin extends User{
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;

    public Admin(int id, String email, String password, String profilePictureURL, String firstName, String lastName) {
        super(id, email, password, profilePictureURL);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
