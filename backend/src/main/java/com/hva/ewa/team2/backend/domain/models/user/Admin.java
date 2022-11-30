package com.hva.ewa.team2.backend.domain.models.user;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

public class Admin extends User{
    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    private String firstName;
    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    private String lastName;

    public Admin(int id, String email, String password, String profilePictureURL, String firstName, String lastName) {
        super(id, email, password, profilePictureURL, Role.ADMIN);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
