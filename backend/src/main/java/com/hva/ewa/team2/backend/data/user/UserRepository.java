package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.domain.models.user.User;

public interface UserRepository {
    User findUserByCredentials(String email, String password);
}
