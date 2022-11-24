package com.hva.ewa.team2.backend.data.user;

import com.hva.ewa.team2.backend.domain.models.user.User;
import com.hva.ewa.team2.backend.rest.user.json.JsonAdminInfo;
import com.hva.ewa.team2.backend.rest.user.json.JsonClientInfo;
import com.hva.ewa.team2.backend.rest.user.json.JsonSpecialistInfo;

import java.util.List;

public interface UserRepository {
    User getUserInfoByCredentials(String email, String password);

    User getUserById(int id);

    String getRoleByUser(User user);

    List<User> getAllUsers();

    List<User> getUsersByRole(String role);

    User saveAdmin(JsonAdminInfo admin);

    User saveSpecialist(JsonSpecialistInfo specialist);

    User saveClient(JsonClientInfo client);

    User deleteUserById(int id);
}
