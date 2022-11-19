package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

public class Skill {

    @Getter @Setter
    private int id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    public int getId() {
        return id;
    }

    public Skill(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
