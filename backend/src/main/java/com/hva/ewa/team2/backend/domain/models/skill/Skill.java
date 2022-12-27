package com.hva.ewa.team2.backend.domain.models.skill;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private SkillGroup skillGroup;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    public Skill() {
    }

    public Skill(Integer id, SkillGroup skillGroup, String name, String description) {
        this.id = id;
        this.skillGroup = skillGroup;
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
