package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

public class UserSkill {

    @Getter @Setter
    private final int id;
    @Getter @Setter
    private final Skill skill;
    @Getter @Setter
    private  int rating;

    public UserSkill(int id, Skill skill, int rating) {
        this.id = id;
        this.skill = skill;
        this.rating = rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
}
