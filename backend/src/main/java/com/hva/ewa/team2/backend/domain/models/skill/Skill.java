package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Skill {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @ManyToOne(targetEntity = SkillGroup.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Integer groupId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    public Skill() {
    }

    public Skill(Integer id, Integer groupId, String name, String description) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.description = description;
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
