package com.hva.ewa.team2.backend.domain.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.hva.ewa.team2.backend.domain.models.skill.Expertise;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.UserSkill;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Specialist extends User {

    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    private String firstName;

    @Getter
    @Setter
    @JsonView(EssentialInfo.class)
    private String lastName;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<UserSkill> skills;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Expertise> expertises;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @JsonIgnore
    private Address address;

    @Getter
    @Setter
    @JsonIgnore
    String resumeURL;

    public Specialist() {
        this.skills = new ArrayList<>();
        this.expertises = new ArrayList<>();
    }

    public Specialist(Integer id, String email, String profilePictureURL, String firstName, String lastName) {
        this(id, email, null, profilePictureURL, firstName, lastName);
    }

    public Specialist(Integer id, String email, String password, String profilePictureURL, String firstName, String lastName) {
        this(id, email, password, profilePictureURL, firstName, lastName, null, null);
    }

    public Specialist(Integer id, String email, String password, String profilePictureURL, String firstName, String lastName, Address address, String resumeURL) {
        super(id, email, password, profilePictureURL, Role.SPECIALIST);
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = new ArrayList<>();
        this.expertises = new ArrayList<>();
        this.address = address;
        this.resumeURL = resumeURL;
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
            return userSkill;
        }
        // Add new user skill
        UserSkill newSkill = new UserSkill(skills.size(), this, skill, rating);
        skills.add(newSkill);
        return newSkill;
    }

    public List<Expertise> updateUserExpertise(ArrayList<Expertise> expertises) {
//        List<UserExpertise> userExpertises = expertises.stream()
//                .map(expertise -> (new UserExpertise(expertise.getId(), this.getId())))
//                .collect(Collectors.toList());

        this.expertises = expertises;
        return expertises;
    }

    @Override
    public String toString() {
        return "{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
