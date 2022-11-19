package com.hva.ewa.team2.backend.domain.usecases.skill;

import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.data.specialist.SpecialistRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import com.hva.ewa.team2.backend.domain.models.skill.UserSkill;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectParticipantAddInfo;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SkillInteractor implements SkillBusinessLogic {

    private final UserRepository userRepo;

    private final SkillRepository skillRepo;
    
    @Autowired
    public SkillInteractor(UserRepository userRepo, SkillRepository skillRepo) {
        this.userRepo = userRepo;
        this.skillRepo = skillRepo;
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillRepo.findAllSkills();
    }

    @Override
    public ArrayList<SkillGroup> getAllSkillGroups() {
       return skillRepo.findAllSkillGroups();
    }

    @Override
    public UserSkill updateUserSkill(int userId, JsonUserSkill jsonBody) {
        User user = userRepo.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        if (!(user instanceof Specialist specialist) ) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a specialist.");
        }

        Skill skill = skillRepo.findSkillById(jsonBody.getSkillId());

        if (skill == null) {
            throw new IllegalArgumentException("The skill with ID " + jsonBody.getSkillId() + " does not exist.");
        }

        if (jsonBody.getSkillRating() < 0 || jsonBody.getSkillRating() > 5) {
            throw new IllegalArgumentException("The skill value of " + jsonBody.getSkillRating() + " is out of bounds.");
        }

        return specialist.updateUserSkill(skill, jsonBody.getSkillRating());
    }
    @Override
    public List<UserSkill> getUserSkills(int userId) {
        User user = userRepo.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        if (!(user instanceof Specialist specialist)) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a specialist.");
        }

        return specialist.getSkills();
    }
}
