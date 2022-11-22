package com.hva.ewa.team2.backend.rest.skill.json;

import lombok.Getter;

import java.io.Serializable;

public class JsonUserSkill implements Serializable {

    @Getter
    private int id;
    @Getter
    private int rating;

    public JsonUserSkill() {}

    public JsonUserSkill(int skillId, int skillRating) {
        this.id = skillId;
        this.rating = skillRating;
    }

}
