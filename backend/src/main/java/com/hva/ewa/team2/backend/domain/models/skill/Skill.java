package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

public class Skill {

    private int id;

    private String name;

    private String description;

//    private SkillGroup group;

    public int getId() {
        return id;
    }

    public Skill(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
//        this.group = group;
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
