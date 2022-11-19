package com.hva.ewa.team2.backend.domain.usecases.skill;

import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import com.hva.ewa.team2.backend.domain.models.skill.UserSkill;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectParticipantAddInfo;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkill;

import java.util.ArrayList;
import java.util.List;

public interface SkillBusinessLogic {

    List<Skill> getAllSkills();
    ArrayList<SkillGroup> getAllSkillGroups();
    UserSkill updateUserSkill(int userId, JsonUserSkill jsonBody);
    List<UserSkill> getUserSkills(int id);

}
