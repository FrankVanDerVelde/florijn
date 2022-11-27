package com.hva.ewa.team2.backend.domain.usecases.auth;

import com.hva.ewa.team2.backend.data.user.UserRepository;
import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthInteractor implements AuthBusinessLogic {

    private final UserRepository userRepo;

    @Autowired
    public AuthInteractor(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getUserInfoByCredentials(String email, String password) {
        return this.userRepo.getUserInfoByCredentials(email, password);
    }
}
