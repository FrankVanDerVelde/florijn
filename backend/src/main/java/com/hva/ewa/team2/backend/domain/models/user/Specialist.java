package com.hva.ewa.team2.backend.domain.models.user;

import com.hva.ewa.team2.backend.domain.models.skill.Skill;

import java.util.ArrayList;
import java.util.List;

public class Specialist extends User {

    private String firstName;
    private String lastName;
    private final List<Skill> skills;

    public Specialist(int id, String email,  String password, String profilePictureURL, String firstName, String lastName) {
        super(id, email, password, profilePictureURL);
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = new ArrayList<>();
        // TODO: implement new skill structure with UserSkill.
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Skill> getSkills() {
        return skills;
    }
}
