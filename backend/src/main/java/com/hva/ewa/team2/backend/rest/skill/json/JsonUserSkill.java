package com.hva.ewa.team2.backend.rest.skill.json;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class JsonUserSkill implements Serializable {

    @Getter @Setter
    private int id;
    @Getter @Setter
    private int rating;

    public JsonUserSkill() {}

    public JsonUserSkill(int skillId, int skillRating) {
        this.id = skillId;
        this.rating = skillRating;
    }

    @Override
    public String toString() {
        return "userSkill{" +
                "id=" + id +
                ", rating=" + rating +
                '}';
    }

}
