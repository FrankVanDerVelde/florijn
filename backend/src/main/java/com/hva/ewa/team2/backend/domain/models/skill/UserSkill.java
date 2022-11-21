package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

public class UserSkill {

    @Getter @Setter
    private int id;
    @Getter @Setter
    private Skill skill;
    @Getter @Setter
    private  int rating;

    public UserSkill() {
    }

    public UserSkill(int id, Skill skill, int rating) {
        this.id = id;
        this.skill = skill;
        this.rating = rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", skill=" + skill +
                ", rating=" + rating +
                '}';
    }
}
