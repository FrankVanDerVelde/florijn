package com.hva.ewa.team2.backend.domain.usecases.skill;

import com.hva.ewa.team2.backend.data.skill.ExpertiseRepository;
import com.hva.ewa.team2.backend.data.skill.SkillGroupRepository;
import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.skill.Expertise;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.skill.SkillGroup;
import com.hva.ewa.team2.backend.domain.models.skill.UserSkill;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.skill.json.JsonUserSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SkillInteractor implements SkillBusinessLogic {

    private final UserRepository userRepo;

    private final SkillRepository skillRepo;

    private final ExpertiseRepository expertiseRepo;

    private final SkillGroupRepository skillGroupRepo;

    @Autowired
    public SkillInteractor(UserRepository userRepo, SkillRepository skillRepo, ExpertiseRepository expertiseRepo, SkillGroupRepository skillGroupRepo) {
        this.userRepo = userRepo;
        this.skillRepo = skillRepo;
        this.expertiseRepo = expertiseRepo;
        this.skillGroupRepo = skillGroupRepo;
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillRepo.getAllSkills();
    }

    @Override
    public ArrayList<SkillGroup> getAllSkillGroups() {
        return skillGroupRepo.findAllSkillGroups();
    }

    @Override
    public UserSkill updateUserSkill(int userId, JsonUserSkill jsonBody) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        if (!(user.get() instanceof Specialist specialist) ) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a specialist.");
        }

        Skill skill = skillRepo.getSkillById(jsonBody.getId());

        if (skill == null) {
            throw new IllegalArgumentException("The skill with ID " + jsonBody.getId() + " does not exist.");
        }

        if (jsonBody.getRating() < 0 || jsonBody.getRating() > 5) {
            throw new IllegalArgumentException("The skill value of " + jsonBody.getRating() + " is out of bounds.");
        }

        UserSkill updatedSkill = specialist.updateUserSkill(skill, jsonBody.getRating());

        userRepo.save(specialist);

        return updatedSkill;
    }

    /*
    Edit rest to send group id along with data, and edit this function to use the group id
     */
    @Override
    public List<UserSkill> updateUserSkillGroup(int userId, List<JsonUserSkill> jsonBody) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        if (!(user.get() instanceof Specialist specialist) ) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a specialist.");
        }

        List<UserSkill> skills = new ArrayList<>();
        for (JsonUserSkill jsu : jsonBody) {
            Skill skill = skillRepo.getSkillById(jsu.getId());
            if (skill == null || jsu.getRating() < 0 || jsu.getRating() > 5) continue;

            skills.add(new UserSkill(0, specialist, skill, jsu.getRating()));
        }

        specialist.setSkills(skills);
        userRepo.save(specialist);
        System.out.println(specialist.getSkills());
        System.out.println(specialist.getSkills().getClass());

        return specialist.getSkills();
    }

    @Override
    public List<UserSkill> getUserSkills(int userId) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        if (!(user.get() instanceof Specialist specialist)) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a specialist.");
        }

        return specialist.getSkills();
    }

    @Override
    public List<Expertise> getAllExpertises() {
        return expertiseRepo.findAllExpertises();
    }

    @Override
    public Expertise getExpertise(int id) {
        return null;
    }

    @Override
    public List<Expertise> getUserExpertises(int userId) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        if (!(user.get() instanceof Specialist specialist)) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a specialist.");
        }
//        System.out.println(specialist.getExpertises());
        return specialist.getExpertises();
    }

    @Override
    public ArrayList<Expertise> updateUserExpertise(int userId, ArrayList<Expertise> userExpertises) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("The user with ID " + userId + " does not exist.");
        }

        if (!(user.get() instanceof Specialist specialist) ) {
            throw new IllegalArgumentException("The user with ID " + userId + " is not a specialist.");
        }

        specialist.setExpertises(userExpertises);

        userRepo.save(specialist);

        return userExpertises;
    }


}
