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
    public JsonUserInfo getUserInfoByCredentials(String email, String password) {
        User user = this.userRepo.getUserInfoByCredentials(email, password);

        if (user != null) {
            String role = this.userRepo.getRoleByUser(user);
            return new JsonUserInfo(user.getId(), role);
        }
        return null;
    }
}
