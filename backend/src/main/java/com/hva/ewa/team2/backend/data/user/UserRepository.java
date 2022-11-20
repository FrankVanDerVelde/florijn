package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.domain.models.user.User;

import java.util.List;

public interface UserRepository {
    User findUserByCredentials(String email, String password);

    User findById(int id);

    // The get all method is for testing
    List<User> getAllUsers();

    List<User> getSpecialists();
}
