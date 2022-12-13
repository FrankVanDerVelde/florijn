package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.data.skill.MemorySkillRepository;
import com.hva.ewa.team2.backend.domain.models.skill.Expertise;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Deprecated
@Repository
public class InMemoryUserRepository implements MemoryUserRepository {

    private final ArrayList<User> users = new ArrayList<>();

    private MemorySkillRepository skillRepo;

    @Autowired
    public InMemoryUserRepository(MemorySkillRepository skillRepo) {
        this.skillRepo = skillRepo;

        this.users.add(new Specialist(1, "withneyk@florijn.com", "test", "/src/assets/avatars/avatar2.avif", "Withney", "Keulen"));
        this.users.add(new Specialist(2, "jant@florijn.com", "test", "/src/assets/avatars/avatar3.avif", "Jan", "Timmermans"));

        this.users.add(new Admin(3, "admin@test.com", "test", null, "Admin", "Test"));
        this.users.add(new Specialist(4, "specialist@test.com", "test", "/src/assets/avatars/avatar3.avif", "Kingsley", "Mckenzie"));
        this.users.add(new Client(5, "contact@ing.nl", "test", "/src/assets/ING-Bankieren-icoon.webp", "ING", "/src/assets/ing-banner.jpg"));

        Address dummyAddress1 = new Address("Amsterdam", "Jan van Galenstraat", 53, "E", "1204EX");
        Address dummyAddress2 = new Address("Hoorn", "Noorder Plantsoen", 12, "", "1623AB");

        Specialist specialist = (new Specialist(6, "specialist2@test.com", "test", "/src/assets/avatars/avatar3.avif", "Sam", "Janssen", dummyAddress1));
        Specialist specialist2 = (new Specialist(7, "specialist3@test.com", "test", "/src/assets/avatars/avatar3.avif", "Jop", "Christensen", dummyAddress2));

        this.users.add(new Client(8, "contact@microsoft.com", "test", "/src/assets/microsoft-logo.png", "Microsoft", "/src/assets/microsoft-banner.jpeg"));

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

        for (int i = 0; i < halfListSize; i++) {
            int randomRating = ThreadLocalRandom.current().nextInt(1, 5 + 1);
            // Add a skill to the specialist with a random rating
            specialist.updateUserSkill(allSkills.get(i), randomRating);
        }

        ArrayList<Expertise> allExpertises = skillRepo.getAllExpertises();

        ArrayList<Expertise> userExpertises = new ArrayList<>();
        int specialistId = specialist.getId();
        for (Expertise expertise: allExpertises) {
            boolean randomBool = ThreadLocalRandom.current().nextBoolean();
            if (randomBool) {
                userExpertises.add(expertise);
            }
        }


        specialist.updateUserExpertise(userExpertises);

        users.add(specialist);
    }

    @Override
    public User getUserInfoByCredentials(String email, String password) {
        return this.users.stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    @Override
    public String getRoleByUser(User user) {
        String role = null;
        if (user instanceof Admin) {
            role = "admin";
        } else if (user instanceof Specialist) {
            role = "specialist";
        } else if (user instanceof Client) {
            role = "client";
        }
        return role;
    }

    @Override
    public User getUserById(int id) {
        return this.users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return this.users;
    }

    @Override
    public List<User> getUsersByRole(String role) {
        List<User> userList = new ArrayList<>();

        for (User user : this.users) {
            String tempRole = this.getRoleByUser(user);

            if (tempRole.equals(role)) {
                userList.add(user);
            }
        }
        return userList;
    }

    @Override
    public User updateUser(User user) {
        this.users.replaceAll(u -> u.getId() == user.getId() ? user : u);
        return user;
    }

    @Override
    public User addUser(String role, User user) {
        user.setId(users.size());
        users.add(user);
        return user;
    }

    @Override
    public User deleteUserById(int id) {
        User user = getUserById(id);
        this.users.remove(user);
        return user;
    }
}
