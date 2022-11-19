package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.data.skill.InMemorySkillRepository;
import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class InMemoryUserRepository implements UserRepository {

    private ArrayList<User> users = new ArrayList<>();


    private SkillRepository skillRepo;

    @Autowired
    public InMemoryUserRepository(SkillRepository skillRepo) {
        this.skillRepo = skillRepo;
        this.users.add(new Admin(0, "admin@test.com", "test", null, "Admin", "Test"));
        this.users.add(new Client(2, "client@test.com", "test", null, "Client", "Test"));

        Specialist specialist = (new Specialist(1, "specialist@test.com", "test", null, "Specialist", "Test"));
        Specialist specialist2 = (new Specialist(3, "specialist@test.com", "test", null, "Specialist", "Test"));

        setSkills(specialist);
        setSkills(specialist2);



    }

    public void setSkills(Specialist specialist) {
        // Give specialist a random set of dummy skills
        ArrayList<Skill> allSkills = skillRepo.findAllSkills();

        // Shuffle skills
        Collections.shuffle(allSkills);

        // Get the size of half the list
        int halfListSize = (int) Math.ceil((double) allSkills.size() / 2);

        for(int i = 0; i < halfListSize; i++) {
            int randomRating = ThreadLocalRandom.current().nextInt(1, 5 + 1);
            // Add a skill to the specialist with a random rating
            specialist.updateUserSkill(allSkills.get(i), randomRating);
        }

        users.add(specialist);
    }

    @Override
    public User findUserByCredentials(String email, String password) {
        return this.users.stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    @Override
    public User findById(int id) {
        return this.users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }
}
