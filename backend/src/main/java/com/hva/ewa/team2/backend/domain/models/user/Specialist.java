package com.hva.ewa.team2.backend.domain.models.user;

import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.UserSkill;

import java.util.ArrayList;
import java.util.List;

public class Specialist extends User {

    private String firstName;
    private String lastName;
    private final List<UserSkill> skills;

    private Address address;

    public Specialist(int id, String email,  String password, String profilePictureURL, String firstName, String lastName) {
        this(id, email, password, profilePictureURL, firstName, lastName, new Address());
        // TODO: implement new skill structure with UserSkill.
    }
    public Specialist(int id, String email,  String password, String profilePictureURL, String firstName, String lastName, Address address) {
        super(id, email, password, profilePictureURL);
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = new ArrayList<>();
        this.address = address;
        // TODO: implement new skill structure with UserSkill.
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public List<UserSkill> getSkills() {
        return skills;
    }

    public UserSkill getUserSkill(Skill skill) {
        return skills.stream()
                .filter(us -> us.getSkill().equals(skill))
                .findFirst()
                .orElse(null);
    }

    public UserSkill updateUserSkill(Skill skill, int rating) {
        UserSkill userSkill = getUserSkill(skill);
        if (userSkill != null) {
            // Update rating
            userSkill.setRating(rating);
            return  userSkill;
        }
        // Add new user skill
        UserSkill newSkill = new UserSkill(0, skill, rating);
        skills.add(newSkill);
        return newSkill;
    }

}
