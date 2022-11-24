package com.hva.ewa.team2.backend.domain.usecases.user;

import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.user.json.*;
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
    public JsonUserInfo getUserInfoByCredentials(String email, String password) {
        User user = this.userRepo.getUserInfoByCredentials(email, password);

        if (user != null) {
            String role = this.userRepo.getRoleByUser(user);
            return new JsonUserInfo(user.getId(), role);
        }
        return null;
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
    public User saveAdmin(JsonAdminInfo body) {
        return this.userRepo.saveAdmin(body);
    }

    @Override
    public User saveSpecialist(JsonSpecialistInfo body) {
        return this.userRepo.saveSpecialist(body);
    }

    @Override
    public User saveClient(JsonClientInfo body) {
        return this.userRepo.saveClient(body);
    }

    @Override
    public User deleteUserById(int id) {
        return this.userRepo.deleteUserById(id);
    }
}
