package com.hva.ewa.team2.backend.rest.skill.json;

import lombok.Getter;

public class JsonUserSkill {

    @Getter
    private int skillId;
    @Getter
    private int skillRating;

    public JsonUserSkill(int skillId, int skillRating) {
        this.skillId = skillId;
        this.skillRating = skillRating;
    }
}
