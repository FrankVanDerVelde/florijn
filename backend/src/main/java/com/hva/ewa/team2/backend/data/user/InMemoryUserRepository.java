package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.data.skill.SkillRepository;
import com.hva.ewa.team2.backend.domain.models.skill.Skill;
import com.hva.ewa.team2.backend.domain.models.user.*;
import com.hva.ewa.team2.backend.rest.user.json.JsonAdminInfo;
import com.hva.ewa.team2.backend.rest.user.json.JsonClientInfo;
import com.hva.ewa.team2.backend.rest.user.json.JsonSpecialistInfo;
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

        Address dummyAddress1 = new Address("Amsterdam", "Jan van Galenstraat", 53 ,"E", "1204EX");
        Address dummyAddress2 = new Address("Hoorn", "Noorder Plantsoen", 12 ,"", "1623AB");

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
    public User findUserByCredentials(String email, String password) {
        return this.users.stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    @Override
    public String findRoleByUser(User user) {
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
    public User findById(int id) {
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
            String tempRole = this.findRoleByUser(user);

            if (tempRole.equals(role)) {
                userList.add(user);
            }
        }
        return userList;
    }

    @Override
    public User saveAdmin(JsonAdminInfo body) {
        Admin admin = new Admin(body.getId(), body.getEmail(), body.getPassword(), body.getAvatarUrl(),
                body.getFirstName(), body.getLastName());
        if (admin.getId() <= 0) {
            admin.setId(this.users.size());
        }

        try {
            int index = this.users.indexOf(findById(admin.getId()));
            this.users.set(index, admin);
        } catch (Exception e) {
            this.users.add(admin);
        }
        return findById(admin.getId());
    }

    @Override
    public User saveSpecialist(JsonSpecialistInfo body) {
        Specialist specialist = new Specialist(body.getId(), body.getEmail(), body.getPassword(), body.getAvatarUrl(),
                body.getFirstName(), body.getLastName());

        if (specialist.getId() <= 0) {
            specialist.setId(this.users.size());
        }

        try {
            int index = this.users.indexOf(findById(specialist.getId()));
            this.users.set(index, specialist);
        } catch (Exception e) {
            this.users.add(specialist);
        }
        return specialist;
    }

    @Override
    public User saveClient(JsonClientInfo body) {
        Client client = new Client(body.getId(), body.getEmail(), body.getPassword(), body.getAvatarUrl(),
                body.getName(), body.getBannerSrc());

        if (client.getId() <= 0) {
            client.setId(this.users.size());
        }

        try {
            int index = this.users.indexOf(findById(client.getId()));
            this.users.set(index, client);
        } catch (Exception e) {
            this.users.add(client);
        }
        return client;
    }

    @Override
    public User deleteById(int id) {
        User user = findById(id);
        this.users.remove(user);
        return user;
    }

    @Override
    public List<User> getSpecialists() {
        return this.users.stream().filter(user -> user instanceof Specialist).toList();
    }}
