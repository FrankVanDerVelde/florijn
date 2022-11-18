package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

public class Skill {

    private int id;

    private String name;

    private String description;

    private int rating_out_of_five;

    public int getId() {
        return id;
    }

    public Skill(int id, String name, String description, int rating_out_of_five) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating_out_of_five = rating_out_of_five;
    }
}
