package com.hva.ewa.team2.backend.domain.usecases.auth;

import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.user.json.JsonUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthInteractor implements AuthBusinessLogic {

    private final UserRepository userRepo;

    @Autowired
    public AuthInteractor(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public JsonUserInfo getAuthInfoByCredentials(String email, String password) {
        List<User> users = this.userRepo.getAllUsers();
        User user = this.userRepo.getAuthInfoByCredentials(users, email, password);

        if (user != null) {
            String role = this.userRepo.getRoleByUser(user);
            return new JsonUserInfo(user.getId(), role);
        }
        return null;
    }
}
