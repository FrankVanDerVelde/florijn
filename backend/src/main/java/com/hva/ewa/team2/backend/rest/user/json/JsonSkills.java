package com.hva.ewa.team2.backend.rest.user.json;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class JsonSkills {

    @Getter
    @Setter
    private List<Integer> skills;

    public JsonSkills() {
    }

    public JsonSkills(List<Integer> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "JsonSkills{" +
                "skills=" + skills +
                '}';
    }
}
