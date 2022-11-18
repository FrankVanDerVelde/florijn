package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

public class UserSkill {

    @Getter @Setter
    private final int id;
    @Getter @Setter
    private final Skill skill;
    @Getter @Setter
    private final int rating_out_of_five;

    public UserSkill(int id, Skill skill, int rating_out_of_five) {
        this.id = id;
        this.skill = skill;
        this.rating_out_of_five = rating_out_of_five;
    }

}
