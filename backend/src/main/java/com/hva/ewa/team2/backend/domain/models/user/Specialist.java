package com.hva.ewa.team2.backend.domain.models.user;

import com.hva.ewa.team2.backend.domain.models.Skill.Skill;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Specialist extends User {

    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String lastName;
    @Getter
    private final List<Skill> skills;

    public Specialist(int id, String email, String profilePictureURL, String firstName, String lastName) {
        super(id, email, profilePictureURL);
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
