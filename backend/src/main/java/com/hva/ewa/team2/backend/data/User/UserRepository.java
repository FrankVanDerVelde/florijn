package com.hva.ewa.team2.backend.data.User;

import com.hva.ewa.team2.backend.domain.models.User.User;

public interface UserRepository {
    User findUserByCredentials(String email, String password);
}
