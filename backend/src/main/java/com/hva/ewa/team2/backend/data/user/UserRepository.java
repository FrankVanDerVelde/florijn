package com.hva.ewa.team2.backend.data.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.domain.models.user.User;

import java.util.List;

public interface UserRepository {
    User getAuthInfoByCredentials(List<User> users, String email, String password);

    User getUserById(int id);

    String getRoleByUser(User user);

    List<User> getAllUsers();

    List<User> getUsersByRole(String role);

    User updateUser(int index, User user);

    User addUser(String role, User user);

    User deleteUserById(int id);
}
