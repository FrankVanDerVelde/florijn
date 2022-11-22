package com.hva.ewa.team2.backend.rest.skill.json;

import com.hva.ewa.team2.backend.domain.models.skill.UserSkill;
import lombok.Getter;

import java.util.ArrayList;

public class JsonUserSkillGroup {

    @Getter
    private int id;

    @Getter
    private ArrayList<UserSkill> skills = new ArrayList<>();

    public JsonUserSkillGroup() {
    }

    public JsonUserSkillGroup(int skillGroupId, ArrayList<UserSkill> userSkillsArrayList) {
        this.id = skillGroupId;
        this.skills = userSkillsArrayList;
    }
}
