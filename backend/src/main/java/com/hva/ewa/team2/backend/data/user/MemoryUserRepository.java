package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.domain.models.user.User;

import java.util.List;

@Deprecated
public interface MemoryUserRepository {
    User getUserInfoByCredentials(String email, String password);

    User getUserById(int id);

    String getRoleByUser(User user);

    List<User> getAllUsers();

    List<User> getUsersByRole(String role);

    User updateUser(User user);

    User addUser(String role, User user);

    User deleteUserById(int id);
}
