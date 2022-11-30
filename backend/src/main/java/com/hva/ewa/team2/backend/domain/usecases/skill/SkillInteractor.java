package com.hva.ewa.team2.backend.domain.usecases.skill;

import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import com.hva.ewa.team2.backend.domain.models.skill.UserSkill;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkill;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkillGroup;
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
        User user = userRepo.getUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        if (!(user instanceof Specialist specialist) ) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a specialist.");
        }

        Skill skill = skillRepo.findSkillById(jsonBody.getId());

        if (skill == null) {
            throw new IllegalArgumentException("The skill with ID " + jsonBody.getId() + " does not exist.");
        }

        if (jsonBody.getRating() < 0 || jsonBody.getRating() > 5) {
            throw new IllegalArgumentException("The skill value of " + jsonBody.getRating() + " is out of bounds.");
        }

        return specialist.updateUserSkill(skill, jsonBody.getRating());
    }

    /*
    Edit rest to send group id along with data, and edit this function to use the group id
     */
    @Override
    public SkillGroup updateUserSkillGroup(int userId, JsonUserSkillGroup jsonBody) {
        User user = userRepo.getUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        if (!(user instanceof Specialist specialist) ) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a specialist.");
        }

        Skill skill = skillRepo.findSkillById(jsonBody.getId());

        if (skill == null) {
            throw new IllegalArgumentException("The skill with ID " + jsonBody.getId() + " does not exist.");
        }

        for (UserSkill updateTarget : jsonBody.getSkills()) {
            if (updateTarget.getRating() < 0 || updateTarget.getRating() > 5) {
                throw new IllegalArgumentException("The skill value of " + updateTarget.getRating() + " is out of bounds.");
            }

            specialist.updateUserSkill(skillRepo.getSkillById(updateTarget.getId()), updateTarget.getRating());
        }

        return skillRepo.getGroupBySkillId(jsonBody.getSkills().get(0).getId());
    }
    @Override
    public List<UserSkill> getUserSkills(int userId) {
        System.out.println(userRepo.getAllUsers());
        User user = userRepo.getUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        if (!(user instanceof Specialist specialist)) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a specialist.");
        }

        return specialist.getSkills();
    }

}
