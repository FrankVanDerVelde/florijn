package com.hva.ewa.team2.backend.domain.models.skill;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SkillGroup {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Getter @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "skillGroup", cascade = CascadeType.ALL)
    private List<Skill> skills;

    public SkillGroup() {}

    public SkillGroup(Integer id, String name, String description) {
        this(id, name, description, new ArrayList<>());
    }

    public SkillGroup(Integer id, String name, String description, ArrayList<Skill> skills) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.skills = skills;
    }

    public void add(Skill skill) {
        skills.add(skill);
    }

    public void remove(Skill skill) {
        skills.remove(skill);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", skills=" + skills +
                '}';
    }
}
