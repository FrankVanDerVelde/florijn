package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Skill {

    @Id
    @GeneratedValue
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    public int getId() {
        return id;
    }

    public Skill() {}

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
