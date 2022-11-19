package com.hva.ewa.team2.backend.data.skill;

import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import org.apache.catalina.Group;

import java.util.ArrayList;

public interface SkillRepository {
    ArrayList<Skill> findAllSkills();
    Skill findSkillById(int id);
    ArrayList<SkillGroup> findAllSkillGroups();
    SkillGroup findGroupById(int id);
}
