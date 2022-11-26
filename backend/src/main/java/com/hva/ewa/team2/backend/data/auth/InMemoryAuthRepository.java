package com.hva.ewa.team2.backend.data.auth;

import com.hva.ewa.team2.backend.domain.models.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryAuthRepository implements AuthRepository {

    @Override
    public User getAuthInfoByCredentials(List<User> users, String email, String password) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .filter(user -> user.getPassword().equals(password))
                .findFirst().orElse(null);
    }
}
