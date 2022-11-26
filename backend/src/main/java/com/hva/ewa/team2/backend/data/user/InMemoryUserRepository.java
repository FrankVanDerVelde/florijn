package com.hva.ewa.team2.backend.data.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final ArrayList<User> users = new ArrayList<>();

    private SkillRepository skillRepo;

    @Autowired
    public InMemoryUserRepository(SkillRepository skillRepo) {
        this.skillRepo = skillRepo;

        this.users.add(new Specialist(0, "withneyk@florijn.com", "test", "/src/assets/avatars/avatar2.avif", "Withney", "Keulen"));
        this.users.add(new Specialist(1, "jant@florijn.com", "test", "/src/assets/avatars/avatar3.avif", "Jan", "Timmermans"));

        this.users.add(new Admin(2, "admin@test.com", "test", null, "Admin", "Test"));
        this.users.add(new Specialist(3, "specialist@test.com", "test", "/src/assets/avatars/avatar3.avif", "Kingsley", "Mckenzie"));
        this.users.add(new Client(4, "contact@ing.nl", "test", "/src/assets/ING-Bankieren-icoon.webp", "ING", "/src/assets/ing-banner.jpg"));

        Specialist specialist = (new Specialist(5, "specialist@test.com", "test", "/src/assets/avatars/avatar3.avif", "Sam", "Janssen"));
        Specialist specialist2 = (new Specialist(6, "specialist@test.com", "test", "/src/assets/avatars/avatar3.avif", "Jop", "Christensen"));

        Address dummyAddress1 = new Address("Amsterdam", "Jan van Galenstraat", 53, "E", "1204EX");
        Address dummyAddress2 = new Address("Hoorn", "Noorder Plantsoen", 12, "", "1623AB");

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

        users.add(specialist);
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
    public User updateUser(int id, JsonNode body) {
        User user = this.getUserById(id);
        int index = users.indexOf(user);

        user.setEmail(body.get("email").asText());
        user.setPassword(body.get("password").asText());
        user.setAvatarUrl(body.get("avatarUrl").asText());

        if (user instanceof Admin) {
            ((Admin) user).setFirstName(body.get("firstName").asText());
            ((Admin) user).setLastName(body.get("lastName").asText());
        } else if (user instanceof Specialist) {
            ((Specialist) user).setFirstName(body.get("firstName").asText());
            ((Specialist) user).setLastName(body.get("lastName").asText());
        } else if (user instanceof Client) {
            ((Client) user).setName(body.get("name").asText());
            ((Client) user).setBannerSrc(body.get("bannerSrc").asText());
        }
        return this.users.set(index, user);
    }

    @Override
    public User addUser(String role, JsonNode body) {
        User user = null;
        int id = users.size();
        String email = body.get("email").asText();
        String password = body.get("password").asText();
        String avatarUrl = body.get("avatarUrl").asText();

        switch (role) {
            case "admin" -> user = new Admin(id, email, password,
                    avatarUrl, body.get("firstName").asText(), body.get("lastName").asText());
            case "specialist" -> user = new Specialist(id, email, password,
                    avatarUrl, body.get("firstName").asText(), body.get("lastName").asText());
            case "client" -> user = new Client(id, email, password,
                    avatarUrl, body.get("name").asText(), body.get("bannerSrc").asText());
        }
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
