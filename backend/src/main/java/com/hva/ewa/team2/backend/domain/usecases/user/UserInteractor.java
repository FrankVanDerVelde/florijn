package com.hva.ewa.team2.backend.domain.usecases.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.user.Admin;
import com.hva.ewa.team2.backend.domain.models.user.Client;
import com.hva.ewa.team2.backend.domain.models.user.Specialist;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserInteractor implements UserBusinessLogic {
    private final UserRepository userRepo;

    @Autowired
    public UserInteractor(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getUserById(int id) {
        return this.userRepo.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepo.getAllUsers();
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return this.userRepo.getUsersByRole(role);
    }

    @Override
    public User updateUser(int id, JsonNode body) {
        User user = this.userRepo.getUserById(id);

        if (user == null)
            throw new IllegalStateException("There is no user found with that id!");

        if (user instanceof Admin) {
            if (body.get("firstName") == null || body.get("lastName") == null)
                throw new IllegalStateException("The fields firstName and/or lastName isn't found!");
        } else if (user instanceof Specialist) {
            if (body.get("firstName") == null || body.get("lastName") == null)
                throw new IllegalStateException("The fields firstName and/or lastName isn't found!");
        } else if (user instanceof Client) {
            if (body.get("name") == null || body.get("bannerSrc") == null)
                throw new IllegalStateException("The fields name and/or bannerURL isn't found!");
        }
        return this.userRepo.updateUser(id, body);
    }

    @Override
    public User addUser(String role, JsonNode body) {
        if (body.get("email") == null || body.get("password") == null || body.get("avatarUrl") == null)
            throw new IllegalStateException("The fields email and/or password and/or avatarUrl isn't found!");

        switch (role) {
            case "admin":
            case "specialist":
                if (body.get("firstName") == null || body.get("lastName") == null)
                    throw new IllegalStateException("The fields firstName and/or lastName isn't found!");
                break;
            case "client":
                if (body.get("name") == null || body.get("bannerSrc") == null)
                    throw new IllegalStateException("The fields name and/or bannerURL isn't found!");
                break;
        }
        return this.userRepo.addUser(role, body);
    }

    @Override
    public User deleteUserById(int id) {
        return this.userRepo.deleteUserById(id);
    }
}
