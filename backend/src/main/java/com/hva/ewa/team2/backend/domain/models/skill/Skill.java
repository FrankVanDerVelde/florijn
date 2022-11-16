package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

public class Skill {

    @Getter
    @Setter
    private final int id;
    @Getter @Setter
    private final String name;
    @Getter @Setter
    private final int rating_out_of_five;

    Skill(int id, String name, int rating_out_of_five) {
        this.id = id;
        this.name = name;
        this.rating_out_of_five = rating_out_of_five;
    }
}
