package com.hva.ewa.team2.backend.domain.usecases.skill;

import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectInfo;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectParticipantAddInfo;

import java.util.List;

public interface SkillBusinessLogic {

    Skill addSkill(int id);

    Skill removeSkill(int id);

    Skill updateSkill(int id);

}
