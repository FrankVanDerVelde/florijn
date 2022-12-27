package com.hva.ewa.team2.backend.rest.skill.json;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class JsonUserSkillGroup {

    @Getter
    private int id;

    @Getter
    private ArrayList<JsonUserSkill> skills = new ArrayList<>();

    public JsonUserSkillGroup() {
    }

    public JsonUserSkillGroup(int skillGroupId, ArrayList<JsonUserSkill> userSkillsArrayList) {
        this.id = skillGroupId;
        this.skills = userSkillsArrayList;
    }

}
