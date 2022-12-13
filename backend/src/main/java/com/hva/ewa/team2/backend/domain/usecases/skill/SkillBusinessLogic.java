package com.hva.ewa.team2.backend.domain.usecases.skill;

import com.hva.ewa.team2.backend.domain.models.skill.*;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkill;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkillGroup;

import java.util.ArrayList;
import java.util.List;

public interface SkillBusinessLogic {

    List<Skill> getAllSkills();
    ArrayList<SkillGroup> getAllSkillGroups();
    UserSkill updateUserSkill(int userId, JsonUserSkill jsonBody);
    List<UserSkill> getUserSkills(int id);
    SkillGroup updateUserSkillGroup(int userId, JsonUserSkillGroup jsonBody);

    List<Expertise> getAllExpertises();
    Expertise getExpertise(int id);
    List<Expertise> getUserExpertises(int id);
    ArrayList<Expertise> updateUserExpertise(int userId, ArrayList<Expertise> userExpertises);
}