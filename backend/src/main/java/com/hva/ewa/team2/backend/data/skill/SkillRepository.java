package com.hva.ewa.team2.backend.data.skill;

import com.hva.ewa.team2.backend.domain.models.skill.Expertise;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;

import java.util.ArrayList;

public interface SkillRepository {
    ArrayList<Skill> findAllSkills();
    Skill findSkillById(int id);
    ArrayList<SkillGroup> findAllSkillGroups();
    SkillGroup findGroupById(int id);
    Skill getSkillById(int id);
    SkillGroup getGroupBySkillId(int id);
    Expertise getExpertiseById(int id);
    ArrayList<Expertise> getAllExpertises();

}
