package com.hva.ewa.team2.backend.domain.usecases.skill;

import com.hva.ewa.team2.backend.domain.models.skill.*;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectParticipantAddInfo;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkill;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkillGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

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
    List<UserExpertise> getUserExpertises(int id);
    ArrayList<UserExpertise> updateUserExpertise(int userId, ArrayList<UserExpertise> userExpertises);
}