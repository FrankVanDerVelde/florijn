package com.hva.ewa.team2.backend.data.skill;

import com.hva.ewa.team2.backend.domain.models.skill.Skill;

import java.util.ArrayList;

public interface SkillRepository {
    ArrayList<Skill> findAll();

    Skill findById(int id);
}
