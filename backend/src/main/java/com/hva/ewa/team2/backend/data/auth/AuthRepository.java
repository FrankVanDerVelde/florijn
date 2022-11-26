package com.hva.ewa.team2.backend.data.auth;

import com.hva.ewa.team2.backend.domain.models.user.User;

import java.util.List;

public interface AuthRepository {
    User getAuthInfoByCredentials(List<User> users, String email, String password);
}
