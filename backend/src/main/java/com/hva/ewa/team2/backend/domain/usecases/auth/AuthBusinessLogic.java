package com.hva.ewa.team2.backend.domain.usecases.auth;

import com.hva.ewa.team2.backend.domain.models.user.User;

public interface AuthBusinessLogic {
    User getUserInfoByCredentials(String email, String password);
}
