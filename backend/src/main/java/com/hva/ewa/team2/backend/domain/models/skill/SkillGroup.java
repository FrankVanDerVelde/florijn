package com.hva.ewa.team2.backend.domain.models.skill;

import java.util.ArrayList;
import java.util.List;

public class SkillGroup {

    private int id;

    private String name;

    private String description;

    private final List<Skill> skills;

    public SkillGroup(int id, String name, String description) {
        this(id, name, description, new ArrayList<>());
    }

    public SkillGroup(int id, String name, String description, List<Skill> skills) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.skills = skills;
    }

    public int getId() {
        return id;
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
