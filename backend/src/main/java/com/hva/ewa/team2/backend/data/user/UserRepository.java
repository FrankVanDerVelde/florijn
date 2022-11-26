package com.hva.ewa.team2.backend.data.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.domain.models.user.User;

import java.util.List;

public interface UserRepository {
    User getUserById(int id);

    String getRoleByUser(User user);

    List<User> getAllUsers();

    List<User> getUsersByRole(String role);

    User updateUser(int id, JsonNode body);

    User addUser(String role, JsonNode body);

    User deleteUserById(int id);
}
